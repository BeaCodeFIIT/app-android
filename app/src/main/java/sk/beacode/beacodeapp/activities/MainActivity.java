package sk.beacode.beacodeapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.fragments.MyEventsFragment_;
import sk.beacode.beacodeapp.fragments.MyProfileFragment;
import sk.beacode.beacodeapp.fragments.MyProfileFragment_;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment_;
import sk.beacode.beacodeapp.managers.EventManager;
import sk.beacode.beacodeapp.managers.ExhibitManager;
import sk.beacode.beacodeapp.managers.InterestManager;
import sk.beacode.beacodeapp.managers.UserManager;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Image;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AppCompatActivity
        implements MyProfileFragment.ProfileListener,
        SearchEventsFragment.SearchEventsListener,
        NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigation;

    @RestService
    UserManager userManager;

    @RestService
    EventManager eventManager;

    @RestService
    InterestManager interestManager;

    @RestService
    ExhibitManager exhibitManager;

    MyEventsFragment myEventsFragment;
    SearchEventsFragment searchEventsFragment;
    MyProfileFragment myProfileFragment;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        myEventsFragment = new MyEventsFragment_();

        searchEventsFragment = new SearchEventsFragment_();
        searchEventsFragment.setSearchEventsListener(this);

        myProfileFragment = new MyProfileFragment_();
        myProfileFragment.setProfileListener(this);
    }

    @AfterViews
    void initViews() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigation.setNavigationItemSelectedListener(this);
        navigation.getMenu().performIdentifierAction(R.id.nav_my_events, 0);
        navigation.getMenu().getItem(0).setChecked(true);

        getUser();
        getEvents();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OptionsItem(R.id.action_settings)
    void settingsSelected() {
        // TODO
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_my_events: {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, myEventsFragment)
                        .commit();
                break;
            }
            case R.id.nav_search_events: {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchEventsFragment)
                        .commit();
                break;
            }
            case R.id.nav_my_profile: {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, myProfileFragment)
                        .commit();
                break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Background
    void getEvents() {
        List<Event> events = eventManager.getEvents().getEvents();

        for (Event e : events) {
            e.getMainImage();
            e.getImages();
            e.setExhibits(exhibitManager.getExhibitsByEventId(e.getId()).getExhibits());
        }

        myEventsFragment.bind(events);
    }

    @Background
    void getUser() {
        user = userManager.getLoggedInUser();
        user.getImage().getBitmap();
        user.setInterests(interestManager.getInterests().getInterests());
        myProfileFragment.bind(user);
    }

    @Override
    public void onChangeUserName(String firstName, String lastName) {
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            myProfileFragment.bind(user);
            onChangeUserNameBackground(firstName, lastName);
        }
    }

    @Background
    void onChangeUserNameBackground(String firstName, String lastName) {
        // TODO
    }

    @Override
    public void onChangeUserPicture(Bitmap picture) {
        if (user != null) {
            if (user.getImage() == null) {
                user.setImage(new Image());
            }
            user.getImage().setBitmap(picture);
            myProfileFragment.bind(user);
            onChangeUserPictureBackground(picture);
        }
    }

    @Background
    void onChangeUserPictureBackground(Bitmap picture) {
        // TODO
    }

    @Override
    public void onAddInterest(Interest interest) {
        if (user != null) {
            if (user.getInterests() != null) {
                user.getInterests().add(interest);
            }
            myProfileFragment.bind(user);
            onAddInterestBackground(interest);
        }
    }

    @Background
    void onAddInterestBackground(Interest interest) {
        interestManager.addInterest(interest);
    }

    @Override
    public void onDeleteInterest(Interest interest) {
        if (user != null) {
            if (user.getInterests() != null) {
                user.getInterests().remove(interest);
            }
            myProfileFragment.bind(user);
            onDeleteInterestBackground(interest);
        }
    }

    @Background
    void onDeleteInterestBackground(Interest interest) {
        interestManager.deleteInterest(interest.getId());
    }

    @Override
    @Background
    public void onSearchResultClick(Event event) {
        event.getMainImage();
        event.getImages();
        event.setExhibits(exhibitManager.getExhibitsByEventId(event.getId()).getExhibits());
        EventActivity.setEvent(event);
        Intent intent = new Intent(this, EventActivity_.class);
        startActivity(intent);
    }
}
