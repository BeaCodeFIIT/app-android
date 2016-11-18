package sk.beacode.beacodeapp.activities;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
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
import sk.beacode.beacodeapp.managers.InterestManager;
import sk.beacode.beacodeapp.managers.UserManager;
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

    @RestService
    InterestManager interestManager;

    MyEventsFragment myEventsFragment;
    SearchEventsFragment searchEventsFragment;
    MyProfileFragment myProfileFragment;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        myEventsFragment = new MyEventsFragment_();
        searchEventsFragment = new SearchEventsFragment_();
        myProfileFragment = new MyProfileFragment_();

        user = userManager.getLoggedInUser().getUser();
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
        interestManager.addInterest(interest);
    }

    @Override
    public void onChangeProfilePhoto(DialogFragment dialog, Bitmap photo) {
        myProfileFragment.setPhoto(photo);
    }
}
