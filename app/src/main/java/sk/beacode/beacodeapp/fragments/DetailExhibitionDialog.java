package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;

import sk.beacode.beacodeapp.R;

/**
 * Created by Veronika on 2.11.2016.
 */

@EFragment(R.layout.fragment_dialog_detail_exhibition)
public class DetailExhibitionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        return dialogBuilder.create();
    }
}
