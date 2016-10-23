package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sk.beacode.beacodeapp.R;

import static android.R.attr.bitmap;
import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static sk.beacode.beacodeapp.fragments.MyProfileFragment.profileImageView;
import static sk.beacode.beacodeapp.fragments.MyProfileFragment.user;

@EFragment(R.layout.fragment_dialog_change_profile)
public class DialogChangeProfileFragment extends DialogFragment {

    @ViewById(R.id.camera)
    Button camera;

    @ViewById(R.id.gallery)
    Button gallery;

    /**
     *
     * @param savedInstanceState
     * @return an alert dialog that allows the user to change his profile picture
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View dialogView = inflater.inflate(R.layout.fragment_dialog_change_profile, null);
        builder.setView(dialogView);
        final TextView cameraBtn = (TextView) dialogView.findViewById(R.id.camera);
        final TextView galleryBtn = (TextView) dialogView.findViewById(R.id.gallery);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        1);
            }
        });
        return builder.create();

    }

    /**
     * The method enables picking an image from a camera or from a gallery
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage = imageReturnedIntent.getData();
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    profileImageView.setImageURI(selectedImage);
                }
                break;

            case 1:
                if(resultCode == RESULT_OK){
                    profileImageView.setImageURI(selectedImage);
                }
                break;
        }

        Context applicationContext = getActivity().getApplicationContext();
        InputStream inputStream = null;
        try {
            inputStream = applicationContext.getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        user.setPhoto(BitmapFactory.decodeStream(inputStream));
    }
}
