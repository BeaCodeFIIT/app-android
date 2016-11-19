package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.EFragment;

import sk.beacode.beacodeapp.R;

;

@EFragment(R.layout.fragment_dialog_add_interest)
public class AddInterestDialog extends DialogFragment {

    public interface AddInterestDialogListener {
        void onAddInterest(DialogFragment dialog, String interestName);
    }

    private AddInterestDialogListener listener;

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
                        String interestName = interestField.getText().toString();
                        if (interestName.length() > 0) {
                            listener.onAddInterest(AddInterestDialog.this, interestName);
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
            listener = (AddInterestDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddInterestDialogListener");
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
