package sk.beacode.beacodeapp.fragments;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.activities.EventActivity;
import sk.beacode.beacodeapp.activities.EventActivity_;
import sk.beacode.beacodeapp.adapters.MyEventsAdapter;
import sk.beacode.beacodeapp.managers.EventManager;
import sk.beacode.beacodeapp.managers.ExhibitManager;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.ExhibitList;
import sk.beacode.beacodeapp.views.ExhibitListItemView;

@EFragment(R.layout.fragment_my_events)
public class MyEventsFragment extends Fragment {

    public interface Listener {
        void onEventItemClick(View view, Event event);
    }

    @RestService
    EventManager eventManager;

    @RestService
    ExhibitManager exhibitManager;

    RecyclerView MyRecyclerView;
    List<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_events);
        }

        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        MyRecyclerView.setLayoutManager(MyLayoutManager);

        getEvents();

        return view;
    }

    /**
     * Defining view components
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //@ViewById(R.id.event_title)
        public TextView titleTextView;

       // @ViewById(R.id.event_description)
        public TextView eventDescription;

       // @ViewById(R.id.event_date)
        public TextView eventStartDate;

        //@ViewById(R.id.event_image)
        public ImageView eventImage;

        private Event event;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.event_title);
            eventDescription = (TextView) v.findViewById(R.id.event_description);
            eventStartDate = (TextView) v.findViewById(R.id.event_date);
            eventImage = (ImageView) v.findViewById(R.id.event_image);
            titleTextView.setOnClickListener(this);
            eventDescription.setOnClickListener(this);
            eventStartDate.setOnClickListener(this);
            eventImage.setOnClickListener(this);

        }


        public void bind(Event event) {
            this.event = event;
        }

        @Override
        public void onClick(View v) {
            EventActivity.setEvent(event);
            Intent intent = new Intent(v.getContext(), EventActivity_.class);
            v.getContext().startActivity(intent);
        }
    }

    @UiThread
    void init() {
        if (events.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyEventsAdapter(events));
        }
    }

    @Background
    void getEvents() {
        events = eventManager.getEvents().getEvents();
        for (Event e : events) {
            e.getMainImage();
            e.getImages();
            e.setExhibitions(exhibitManager.getByEventId(e.getId()).getExhibits());
        }
        init();
    }

    public void bind(List<Event> events) {
        this.events = events;
    }
}