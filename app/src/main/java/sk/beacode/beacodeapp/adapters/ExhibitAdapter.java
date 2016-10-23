package sk.beacode.beacodeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.views.ExhibitListItemView;
import sk.beacode.beacodeapp.views.ExhibitListItemView_;

public class ExhibitAdapter extends ArrayAdapter<Exhibit> {

    public ExhibitAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Exhibit exhibit = getItem(position);

        ExhibitListItemView exhibitListItemView;
        if (convertView == null) {
            exhibitListItemView = ExhibitListItemView_.build(getContext());
        } else {
            exhibitListItemView = (ExhibitListItemView) convertView;
        }

        exhibitListItemView.bind(exhibit);


        return exhibitListItemView;
    }
}
