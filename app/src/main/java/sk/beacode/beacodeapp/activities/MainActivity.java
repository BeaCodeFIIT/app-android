package sk.beacode.beacodeapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.fragments.MyEventsFragment_;
import sk.beacode.beacodeapp.fragments.MyProfileFragment;
import sk.beacode.beacodeapp.fragments.MyProfileFragment_;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment;
import sk.beacode.beacodeapp.fragments.SearchEventsFragment_;
import sk.beacode.beacodeapp.managers.EventApi;
import sk.beacode.beacodeapp.managers.ExhibitApi;
import sk.beacode.beacodeapp.managers.InterestApi;
import sk.beacode.beacodeapp.managers.Manager;
import sk.beacode.beacodeapp.managers.UserApi;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.EventList;
import sk.beacode.beacodeapp.models.ExhibitList;
import sk.beacode.beacodeapp.models.Image;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.InterestList;
import sk.beacode.beacodeapp.models.User;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AppCompatActivity
        implements MyProfileFragment.ProfileListener,
        SearchEventsFragment.SearchEventsListener,
        MyEventsFragment.MyEventsListener,
        NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigation;

    MyEventsFragment myEventsFragment;
    SearchEventsFragment searchEventsFragment;
    MyProfileFragment myProfileFragment;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Manager.initialize(deviceId);

        myEventsFragment = new MyEventsFragment_();
        myEventsFragment.setMyEventsListener(this);

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
        onMyEventsRefresh();
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
    void getUser() {
        UserApi userApi = Manager.getInstance().getUserApi();
        Call<User> userCall = userApi.getLoggedInUser();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                //user.getImage().getBitmap();

                InterestApi interestApi = Manager.getInstance().getInterestApi();
                Call<InterestList> interestCall = interestApi.getInterests();
                interestCall.enqueue(new Callback<InterestList>() {
                    @Override
                    public void onResponse(Call<InterestList> call, Response<InterestList> response) {
                        user.setInterests(response.body().getInterests());
                    }

                    @Override
                    public void onFailure(Call<InterestList> call, Throwable t) {

                    }
                });
                myProfileFragment.bind(user);
                MainActivity.this.user = user;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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
    public void onChangeUserPicture(Uri picture) {
        if (user != null) {
            if (user.getImage() == null) {
                user.setImage(new Image());
            }
            user.getImage().setUri(picture);
            myProfileFragment.bind(user);
            onChangeUserPictureBackground(picture);
        }
    }

    @Background
    void onChangeUserPictureBackground(Uri picture) {
        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picture);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            UserApi userApi = Manager.getInstance().getUserApi();
            Call<Void> interestCall = userApi.updateImage(stream.toByteArray());
            interestCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // TODO
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // TODO
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddInterest(Interest interest) {
        if (user != null) {
            System.out.println(interest.getName());
            if (user.getInterests() != null) {
                user.getInterests().add(0,interest);
            }
            myProfileFragment.bind(user);
            onAddInterestBackground(interest);
        }
    }

    @Background
    void onAddInterestBackground(Interest interest) {
        System.out.println("interest");
        InterestApi interestApi = Manager.getInstance().getInterestApi();
        Call<Void> interestCall = interestApi.addInterest(interest);
        interestCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // TODO
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // TODO
            }
        });
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
        InterestApi interestApi = Manager.getInstance().getInterestApi();
        Call<Void> interestCall = interestApi.deleteInterest(interest.getId());
        interestCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // TODO
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    @Background
    public void onSearchResultClick(final Event event) {
        event.getMainImage();
        event.getImages();

        ExhibitApi exhibitApi = Manager.getInstance().getExhibitApi();
        Call<ExhibitList> exhibitCall = exhibitApi.getExhibitsByEventId(event.getId());
        exhibitCall.enqueue(new Callback<ExhibitList>() {
            @Override
            public void onResponse(Call<ExhibitList> call, Response<ExhibitList> response) {
                event.setExhibits(response.body().getExhibits());
            }

            @Override
            public void onFailure(Call<ExhibitList> call, Throwable t) {

            }
        });

        EventActivity.event = event;
        Intent intent = new Intent(this, EventActivity_.class);
        startActivity(intent);
    }

    @Override
    @Background
    public void onMyEventsRefresh() {
        EventApi eventApi = Manager.getInstance().getEventApi();
        Call<EventList> eventCall = eventApi.getEvents();
        eventCall.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                List<Event> events = response.body().getEvents();
                for (Event e : events) {
                    e.getMainImage();
                    e.getImages();
                    if (null != e.getCategories()) {
                        for (final Category category : e.getCategories()) {
                            ExhibitApi exhibitApi = Manager.getInstance().getExhibitApi();
                            Call<ExhibitList> exhibitCall = exhibitApi.getExhibitsByEventId(category.getId());
                            exhibitCall.enqueue(new Callback<ExhibitList>() {
                                @Override
                                public void onResponse(Call<ExhibitList> call, Response<ExhibitList> response) {
                                    category.setExhibits(response.body().getExhibits());
                                }

                                @Override
                                public void onFailure(Call<ExhibitList> call, Throwable t) {

                                }
                            });
                        }
                    }
                }

                myEventsFragment.bind(events);
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {

            }
        });
    }

    public DrawerLayout getDrawer(){
        return this.drawer;
    }

    @Override
    public void onMyEventsClick(Event event) {
    }
}
