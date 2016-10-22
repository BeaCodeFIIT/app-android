package sk.beacode.beacodeapp.fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.adapters.EventListAdapter;
import sk.beacode.beacodeapp.models.Event;

@EFragment(R.layout.fragment_search_events)
public class SearchEventsFragment extends Fragment {

    @ViewById(R.id.SearchBox)
    SearchView searchView;

    @ViewById(R.id.Results)
    TextView results;

    @ViewById(R.id.ResultsView)
    ListView resultsView;

    @AfterViews
    void initViews() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findEvent(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Bean
    EventListAdapter adapter;

    public List<Event> findEvent(String query){
        Event event1 = new Event();
        event1.setName("Autosalón Bratislava");

        Event event2 = new Event();
        event2.setName("Autosalón Expo");

        Event event3 = new Event();
        event3.setName("Výstavisko Incheba");

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        List<Event> foundEvents = new ArrayList<>();

        for(Event e : events){
            if(e.getName() != null && e.getName().contains(query)){
                foundEvents.add(e);
            }
        }

        adapter.setEvent(foundEvents);
        return foundEvents;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_search_events);
        }
        return null;
    }

    @AfterViews
    void bindAdapter() {
        resultsView.setAdapter(adapter);
    }
}
