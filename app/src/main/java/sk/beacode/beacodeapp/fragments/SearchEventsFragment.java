package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.adapters.SearchEventsAdapter;
import sk.beacode.beacodeapp.managers.EventManager;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.EventList;

import static android.R.attr.tag;

@EFragment(R.layout.fragment_search_events)
public class SearchEventsFragment extends Fragment {

    @ViewById(R.id.SearchBox)
    SearchView searchView;

    @ViewById(R.id.Results)
    TextView results;

    @ViewById(R.id.ResultsView)
    ListView resultsView;

    @ViewById(R.id.buttonDetailXXX)
    Button buttonDetail;

    @Bean
    SearchEventsAdapter adapter;

    @RestService
    EventManager eventManager;

    List<Event> resultList;

    @AfterViews
    void initViews() {
        resultsView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchEvents(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchEvents(newText);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_search_events);
        }
        return null;
    }

    public void bind(List<Event> events) {
        adapter.setEvents(events);
    }

    @Background
    public void searchEvents(String query) {
        resultList = eventManager.getEventsByNamePart(query).getEvents();
        showResults();
    }

    @UiThread
    public void showResults() {
        bind(resultList);
    }
}
