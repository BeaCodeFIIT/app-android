package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cunoraz.tagview.Tag;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.R;

@EFragment(R.layout.fragment_dialog_add_interest)
public class DialogAddInterestFragment extends DialogFragment {

    @ViewById(R.id.new_interest)
    EditText newInterest;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_dialog_add_interest, null))
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String interest_name = (String) newInterest.getText().toString();
                        Tag myTag1 = new Tag(interest_name);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddInterestFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
