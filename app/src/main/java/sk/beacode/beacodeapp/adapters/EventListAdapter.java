package sk.beacode.beacodeapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.views.ResultItemView;
import sk.beacode.beacodeapp.views.ResultItemView_;

/**
 * Created by Veronika on 23.10.2016.
 */

@EBean
public class EventListAdapter extends BaseAdapter{

        List<Event> event = new ArrayList<>();

        @RootContext
        Context context;

        @AfterInject
        void initAdapter() {
            event = new ArrayList<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ResultItemView resultsItemView;
            if (convertView == null) {
                resultsItemView = ResultItemView_.build(context);
            } else {
                resultsItemView = (ResultItemView) convertView;
            }

            resultsItemView.bind(getItem(position));

            return resultsItemView;
        }

        @Override
        public int getCount() {
            return event.size();
        }

        @Override
        public Event getItem(int position) {
            return event.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    public void setEvent(List<Event> event) {
        this.event = event;
        notifyDataSetChanged();
    }
}
