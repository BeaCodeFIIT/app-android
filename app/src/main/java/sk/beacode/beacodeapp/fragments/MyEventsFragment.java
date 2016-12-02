package sk.beacode.beacodeapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.activities.EventActivity;
import sk.beacode.beacodeapp.activities.EventActivity_;
import sk.beacode.beacodeapp.adapters.MyEventsAdapter;
import sk.beacode.beacodeapp.models.Event;

@EFragment(R.layout.fragment_my_events)
public class MyEventsFragment extends Fragment {

    public interface MyEventsListener {
        void onMyEventsRefresh();
        void onMyEventsClick(Event event);
    }

    List<Event> events;

    private MyEventsAdapter adapter = new MyEventsAdapter();

    @ViewById(R.id.cardView)
    RecyclerView myRecyclerView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    MyEventsListener listener;

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
            v.setOnClickListener(this);

        }


        public void bind(Event event) {
            this.event = event;
        }

        @Override
        public void onClick(View v) {
            EventActivity.event = event;
            Intent intent = new Intent(v.getContext(), EventActivity_.class);
            v.getContext().startActivity(intent);
        }
    }

    public void setMyEventsListener(MyEventsListener listener) {
        this.listener = listener;
    }

    @AfterViews
    void initViews() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_events);
            actionBar.show();
        }

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setAdapter(adapter);

        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        myRecyclerView.setLayoutManager(myLayoutManager);

        if (events == null) {
            swipeRefreshLayout.setRefreshing(true);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onMyEventsRefresh();
                }
            }
        });
    }

    @UiThread
    public void bind(List<Event> events) {
        this.events = events;
        adapter.setData(events);
        swipeRefreshLayout.setRefreshing(false);
    }
}