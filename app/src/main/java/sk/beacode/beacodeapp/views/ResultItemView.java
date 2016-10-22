package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Event;

/**
 * Created by Veronika on 22.10.2016.
 */

@EViewGroup(R.layout.view_results_item)
public class ResultItemView extends LinearLayout {

        @ViewById (R.id.ResultItemView)
        TextView resultItemView;

        public ResultItemView(Context context) {
            super(context);
        }

        public void bind(Event event) {
            resultItemView.setText(event.getName());
        }
}
