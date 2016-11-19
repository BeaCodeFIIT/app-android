package sk.beacode.beacodeapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.activities.EventActivity;
import sk.beacode.beacodeapp.activities.EventActivity_;
import sk.beacode.beacodeapp.adapters.SearchEventsAdapter;
import sk.beacode.beacodeapp.managers.EventManager;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.EventQuery;
import sk.beacode.beacodeapp.views.RecentItemView;

@EFragment(R.layout.fragment_search_events)
public class SearchEventsFragment extends Fragment {

    @ViewById(R.id.search_view)
    FloatingSearchView searchView;

    @ViewById(R.id.recently_search_list)
    ListView recentView;

    @Bean
    SearchEventsAdapter adapter;

    @RestService
    EventManager eventManager;

    private List<Event> lastResults;
    private List<Event> recentResults = new ArrayList<>();

    @AfterViews
    void initViews() {
        adapter.setEvents(recentResults);
        recentView.setAdapter(adapter);

        adapter.setOnClickListener(new RecentItemView.OnClickListener() {
            @Override
            public void onClick(RecentItemView view, Event event) {
                EventActivity.setEvent(event);
                Intent intent = new Intent(getActivity(), EventActivity_.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                searchEvents(newQuery);
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                recentResults.add((Event) searchSuggestion);
                adapter.setEvents(recentResults);
                EventActivity.setEvent((Event) searchSuggestion);
                Intent intent = new Intent(getActivity(), EventActivity_.class);
                startActivity(intent);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                searchEvents(currentQuery);
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

    @Background
    public void searchEvents(String query) {
        lastResults = eventManager.getEventsByNamePart(new EventQuery(query)).getEvents();
        showResults();
    }

    @UiThread
    public void showResults() {
        if (lastResults != null) {
            searchView.swapSuggestions(lastResults);
        }
    }
}
