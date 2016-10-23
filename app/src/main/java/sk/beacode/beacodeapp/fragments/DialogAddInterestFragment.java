package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.cunoraz.tagview.Tag;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Interest;

import static sk.beacode.beacodeapp.fragments.MyProfileFragment.tagGroup;
import static sk.beacode.beacodeapp.fragments.MyProfileFragment.tags;
import static sk.beacode.beacodeapp.fragments.MyProfileFragment.user;

@EFragment(R.layout.fragment_dialog_add_interest)
public class DialogAddInterestFragment extends DialogFragment{

    @ViewById(R.id.new_interest)
    EditText newInterest;

    public static String newIntrestName = "";

    /**
     *
     * @param savedInstanceState
     * @return an alert dialog that allows the user to add new interest
     */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View dialogView = inflater.inflate(R.layout.fragment_dialog_add_interest, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.new_interest);

        dialogBuilder
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                      newIntrestName = edt.getText().toString();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddInterestFragment.this.getDialog().cancel();
                    }
                });
        return dialogBuilder.create();
    }

    public void onAttach(Activity activity) {
        super.onAttach(getActivity());
        newIntrestName = "";
    }

    /**
     * Method is called when the alert dialog fragment has been detached.
     * In the method is defined adding tag in the list of tags and adding an interest in the user's list of interest
     */
    @Override
    public void onDetach() {

        super.onDetach();
        if (newIntrestName.length() != 0){
            Interest interest = new Interest();
            interest.setName(newIntrestName);
            user.getInterests().add(interest);

            Tag myTag = new Tag(user.getInterests().get(user.getInterests().size() - 1).getName());
            tags.add(myTag);
            myTag.deleteIndicatorColor = Color.GRAY;
            myTag.layoutColor = Color.WHITE;
            myTag.layoutBorderSize = 1;
            myTag.layoutBorderColor = Color.GRAY;
            myTag.tagTextColor = Color.GRAY;
            myTag.radius = 20;
            myTag.isDeletable = true;
            tagGroup.addTag(myTag);

        }
    }
}
