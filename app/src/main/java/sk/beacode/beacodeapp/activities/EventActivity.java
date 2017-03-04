package sk.beacode.beacodeapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.adapters.CategoryAdapter;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.views.ExhibitListItemView;
import sk.beacode.beacodeapp.views.ExhibitListView;
import sk.beacode.beacodeapp.views.HorizontalGalleryView;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity implements ExhibitListView.ExhibitListListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @ViewById(R.id.main_photo)
    ImageView mainPhotoView;

    @ViewById(R.id.description)
    TextView descriptionView;

    @ViewById(R.id.gallery)
    HorizontalGalleryView gallery;

    @ViewById(R.id.exhibits)
    ExpandableListView mExhibits;

    public static Event event;

    CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @AfterViews
    void initViews() {
        setPhotos();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (event == null) {
            return;
        }

        collapsingToolbar.setTitle(event.getName());

        descriptionView.setText(event.getDescription());

        if (null != event.getCategories()){
            mAdapter = new CategoryAdapter(this, event.getCategories());
            mExhibits.setAdapter(mAdapter);
        }
        //        exhibits.bind(event.getExhibits());
//        exhibits.setStartTourListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavigationActivity.event = event;
//                Intent intent = new Intent(EventActivity.this, NavigationActivity_.class);
//                startActivity(intent);
//            }
//        });
//        exhibits.setExhibitOnClickListener(this);
    }

    void setPhotos() {
        Glide.with(this).load(event.getMainImage().getUri()).asBitmap().listener(new RequestListener<Uri, Bitmap>() {
            @Override
            public boolean onException(Exception e, Uri model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (resource != null && !resource.isRecycled()) {
                    mainPhotoView.setImageBitmap(resource);
                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch swatch = palette.getVibrantSwatch();
                            if (swatch != null) {
                                int appBarColor = swatch.getRgb();

                                float[] hsv = new float[3];
                                Color.colorToHSV(appBarColor, hsv);
                                hsv[2] *= 0.8;
                                int statusBarColor = Color.HSVToColor(hsv);

                                collapsingToolbar.setContentScrimColor(appBarColor);
                                collapsingToolbar.setStatusBarScrimColor(statusBarColor);
                            }
                        }
                    });
                }
                return true;
            }
        });

        gallery.bind(event.getImages());
    }

    @Override
    public void onExhibitItemClick(Exhibit exhibit) {
        ExhibitionDetailDialog dialog = new ExhibitionDetailDialog();
        dialog.bind(exhibit);
        dialog.show(getFragmentManager(), "");
    }
}
