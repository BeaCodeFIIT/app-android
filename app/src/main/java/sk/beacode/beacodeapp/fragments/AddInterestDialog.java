package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Interest;

/**
 * Add interest dialog
 */
public class AddInterestDialog extends DialogFragment {
	private MyProfileFragment.ProfileListener listener;

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
			listener = (MyProfileFragment.ProfileListener) activity;
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
