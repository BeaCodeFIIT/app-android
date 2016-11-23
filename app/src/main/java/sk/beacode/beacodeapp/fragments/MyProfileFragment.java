package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Image;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EFragment(R.layout.fragment_my_profile)
public class MyProfileFragment extends Fragment {

    public interface ProfileListener {
        void onChangeUserName(String firstName, String lastName);
        void onChangeUserPicture(Bitmap picture);
        void onAddInterest(Interest interest);
        void onDeleteInterest(Interest interest);
    }

    ProfileListener listener;

    private User user;

    @ViewById(R.id.picture)
    ImageView pictureView;

    @ViewById(R.id.name)
    TextView nameView;

    @ViewById(R.id.tags)
    TagView tagView;

    @ViewById(R.id.add_interest)
    Button addInterestButton;

    public static class AddInterestDialog extends DialogFragment {
        private ProfileListener listener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.fragment_dialog_add_interest, null);
            dialogBuilder.setView(dialogView);

            dialogBuilder
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText interestField = (EditText) getDialog().findViewById(R.id.new_interest);
                            String name = interestField.getText().toString();
                            if (name.length() > 0) {
                                listener.onAddInterest(new Interest().setName(name));
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AddInterestDialog.this.getDialog().cancel();
                        }
                    });
            return dialogBuilder.create();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            try {
                listener = (ProfileListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement ProfileListener");
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
        }
    }

    public static class ChangeProfilePhotoDialog extends DialogFragment {
        private ProfileListener listener;

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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            try {
                listener = (ProfileListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement ProfileListener");
            }
        }

        public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            Uri imageUri = imageReturnedIntent.getData();
            if ((requestCode == 0 || requestCode == 1) && resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    listener.onChangeUserPicture(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.getDialog().cancel();
            }
        }
    }

    public void setProfileListener(ProfileListener listener) {
        this.listener = listener;
    }

    private Tag createTag(String name) {
        Tag t = new Tag(name);
        t.deleteIndicatorColor = Color.GRAY;
        t.layoutColor = Color.WHITE;
        t.layoutBorderSize = 1;
        t.layoutBorderColor = Color.GRAY;
        t.tagTextColor = Color.GRAY;
        t.radius = 20;
        t.isDeletable = true;
        return t;
    }

    @Click(R.id.picture)
    void onPictureViewClick() {
        ChangeProfilePhotoDialog dialog = new ChangeProfilePhotoDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), null);
    }

    @Click(R.id.add_interest)
    void onAddInterestButtonClick() {
        AddInterestDialog dialog = new AddInterestDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), null);
    }

    @AfterViews
    void initViews() {
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.title_fragment_my_profile);
                actionBar.show();
            }
        }

        if (user == null) {
            return;
        }

        Image image = user.getImage();
        if (pictureView != null && image != null) {
            pictureView.setImageBitmap(image.getBitmap());
        }

        if (nameView != null) {
            nameView.setText(user.getFirstName() + " " + user.getLastName());
        }

        if (tagView != null) {
            List<Tag> tags = new ArrayList<>();
            for (Interest i : user.getInterests()) {
                tags.add(createTag(i.getName()));
            }
            tagView.addTags(tags);

            tagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {
                @Override
                public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                    showDeleteInterestDialog(tag);
                }
            });

            tagView.setOnTagLongClickListener(new TagView.OnTagLongClickListener() {
                @Override
                public void onTagLongClick(Tag tag, int position) {
                    showDeleteInterestDialog(tag);
                }
            });
        }
    }

    private void showDeleteInterestDialog(final Tag tag){
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to delete this interest?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Interest> toDelete = new ArrayList<>();
                        for (Interest interest : user.getInterests()) {
                            if (interest.getName().equals(tag.text)) {
                                toDelete.add(interest);
                            }
                        }
                        for (Interest interest : toDelete) {
                            if (listener != null) {
                                listener.onDeleteInterest(interest);
                            }
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    public void bind(User user) {
        this.user = user;
        initViews();
    }
}
