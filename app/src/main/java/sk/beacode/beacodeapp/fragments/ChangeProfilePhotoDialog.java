package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import sk.beacode.beacodeapp.R;

/**
 * Change profile photo dialog
 */
public class ChangeProfilePhotoDialog extends DialogFragment {
	private MyProfileFragment.ProfileListener listener;

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
			listener = (MyProfileFragment.ProfileListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement ProfileListener");
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		Uri imageUri = imageReturnedIntent.getData();
		if ((requestCode == 0 || requestCode == 1) && resultCode == Activity.RESULT_OK) {
			listener.onChangeUserPicture(imageUri);
			this.getDialog().cancel();
		}
	}
}