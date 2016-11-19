package sk.beacode.beacodeapp.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.DetailExhibitionDialog;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.views.ExhibitListItemView;
import sk.beacode.beacodeapp.views.ExhibitListView;
import sk.beacode.beacodeapp.views.HorizontalGalleryView;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity {

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

    @ViewById(R.id.exhibit_list)
    ExhibitListView exhibitions;

    private static Event event;

    public static void setEvent(Event event) {
        EventActivity.event = event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        exhibitions.bind(event.getExhibitions());
        exhibitions.setStartTourListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
        exhibitions.setExhibitOnClickListener(new ExhibitListView.Listener() {
            @Override
            public void onExhibitItemClick(ExhibitListItemView view, Exhibit exhibit) {
                DetailExhibitionDialog dialog = new DetailExhibitionDialog();
                dialog.bind(exhibit);
                dialog.show(getFragmentManager(), "");
            }
        });
    }

    void setPhotos() {
        Bitmap mainPhoto = event.getMainImage();
        if (mainPhoto != null && !mainPhoto.isRecycled()) {
            mainPhotoView.setImageBitmap(mainPhoto);
            Palette.from(mainPhoto).generate(new Palette.PaletteAsyncListener() {
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

        gallery.bind(event.getImages());
    }

}
