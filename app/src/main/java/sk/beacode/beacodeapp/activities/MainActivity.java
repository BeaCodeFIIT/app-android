package sk.beacode.beacodeapp.activities;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.AddInterestDialog;
import sk.beacode.beacodeapp.fragments.ChangeProfilePhotoDialog;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.fragments.MyEventsFragment_;
import sk.beacode.beacodeapp.fragments.MyProfileFragment;
import sk.beacode.beacodeapp.fragments.MyProfileFragment_;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment_;
import sk.beacode.beacodeapp.managers.EventManager;
import sk.beacode.beacodeapp.managers.UserManager;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AddInterestDialog.AddInterestDialogListener,
        ChangeProfilePhotoDialog.ChangeProfilePhotoDialogListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @RestService
    UserManager userManager;

    @RestService
    EventManager eventManager;

    MyEventsFragment myEventsFragment;
    SearchEventsFragment searchEventsFragment;
    MyProfileFragment myProfileFragment;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myEventsFragment = new MyEventsFragment_();
        searchEventsFragment = new SearchEventsFragment_();
        myProfileFragment = new MyProfileFragment_();

        // TODO
        // user = userManager.getUserByName("aaa");
        user = new User();

        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();
        Interest interest4 = new Interest();
        Interest interest5 = new Interest();
        List<Interest> listOfInterests = new ArrayList<>();

        interest1.setName("Cars");
        listOfInterests.add(interest1);
        interest2.setName("Technology");
        listOfInterests.add(interest2);
        interest3.setName("Computers");
        listOfInterests.add(interest3);
        interest4.setName("Nature");
        listOfInterests.add(interest4);
        interest5.setName("Food");
        listOfInterests.add(interest5);

        user.setName("Michal");
        user.setSurname("Moravksy");
        user.setInterests(listOfInterests);
        user.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.my_profile_picture));
    }

    @AfterViews
    void initViews() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().performIdentifierAction(R.id.nav_my_events, 0);
        navigationView.getMenu().getItem(0).setChecked(true);
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

                // TODO
                // List<Event> events = eventManager.getEvents();

                Bitmap[] photos = {
                        BitmapFactory.decodeResource(getResources(), R.drawable.image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.images),
                };

                List<Event> events = new ArrayList<>();

                for (int i = 0; i < 4; ++i) {
                    Event e = new Event();
                    e.setName("Lorem ipsum");
                    e.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                    e.setStart(new GregorianCalendar(2016, 10, 30).getTime());
                    e.setEnd(new GregorianCalendar(2016, 10, 30).getTime());
                    e.setMainPhoto(photos[i % 2]);

                    List<Bitmap> p = new ArrayList<>();

                    for (int ii = 0; ii < 5; ++ii) {
                        p.add(photos[ii % 2]);
                    }

                    e.setPhotos(p);

                    List<Exhibit> exhibits = new ArrayList<>();

                    for (int ii = 0;  ii < 3; ++ii) {
                        Exhibit ex = new Exhibit();
                        ex.setName("Lorem ipsum");
                        ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                        ex.setPhoto(photos[ii % 2]);
                        exhibits.add(ex);
                    }

                    e.setExhibitions(exhibits);
                    events.add(e);
                }

                myEventsFragment.bind(events);

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

                myProfileFragment.bind(user);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, myProfileFragment)
                        .commit();
                break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAddInterest(DialogFragment dialog, String interestName) {
        Interest interest = new Interest().setName(interestName);
        myProfileFragment.addInterest(interest);
    }

    @Override
    public void onChangeProfilePhoto(DialogFragment dialog, Bitmap photo) {
        myProfileFragment.setPhoto(photo);
    }
}
