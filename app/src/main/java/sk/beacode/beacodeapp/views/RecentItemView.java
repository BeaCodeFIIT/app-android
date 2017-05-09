package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.models.Event;

/**
 * Recent item displayed in the search fragment
 */
@EViewGroup(android.R.layout.simple_expandable_list_item_1)
public class RecentItemView extends LinearLayout {

    public interface OnClickListener {
        void onClick(RecentItemView view, Event event);
    }

    @ViewById (android.R.id.text1)
    TextView name;

    private Event event;

    private OnClickListener listener;

    public RecentItemView(Context context) {
        super(context);
    }

    public void bind(Event event) {
        this.event = event;
        name.setText(event.getName());
    }

    public void setOnClickListener(RecentItemView.OnClickListener l) {
        listener = l;
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(RecentItemView.this, event);
            }
        });
    }
}
