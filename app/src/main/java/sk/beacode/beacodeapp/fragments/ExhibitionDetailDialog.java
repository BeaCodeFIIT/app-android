package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EFragment;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Exhibit;

/**
 * Exhibit detail dialog
 */
@EFragment(R.layout.fragment_dialog_detail_exhibition)
public class ExhibitionDetailDialog extends DialogFragment {

    public interface ExhibitDetailListener {
        void onExhibitionDetailClose();
    }

    private ExhibitDetailListener listener;

    private Exhibit exhibit;

    public void setListener(ExhibitDetailListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (listener != null) {
            listener.onExhibitionDetailClose();
        }
    }

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

        if (exhibit != null) {
            TextView name = (TextView) dialogView.findViewById(R.id.title_exibit);
            name.setText(exhibit.getName());
            TextView date = (TextView) dialogView.findViewById(R.id.date);
            if (exhibit.getStart() != null && exhibit.getEnd() != null) {
                date.setText(String.format("%d - %d", exhibit.getStart().toLocaleString(), exhibit.getEnd().toLocaleString()));
            }
            TextView description = (TextView) dialogView.findViewById(R.id.detailExhibitionXXX);
            description.setText(exhibit.getDescription());
            System.out.println("qqqqq " + exhibit.getMainImage().getUri());
            ImageView photo = (ImageView) dialogView.findViewById(R.id.imageDetailExhibition);
            Glide.with(this).load(exhibit.getMainImage().getUri()).into(photo);
            //photo.setImageBitmap(exhibit.getMainImage());
        }

        return dialogBuilder.create();
    }

    public void bind(Exhibit exhibit) {
        this.exhibit = exhibit;
    }
}
