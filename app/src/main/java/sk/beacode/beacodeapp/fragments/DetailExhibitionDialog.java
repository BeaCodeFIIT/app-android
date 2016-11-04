package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_dialog_detail_exhibition, null);
        dialogBuilder.setView(dialogView);
        Button closeBtn = (Button) dialogView.findViewById(R.id.close_dialog_detail_exibition);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDialog().cancel();
            }
        });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
