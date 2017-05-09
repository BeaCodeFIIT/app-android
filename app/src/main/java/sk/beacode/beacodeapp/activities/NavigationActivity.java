package sk.beacode.beacodeapp.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.davemorrissey.labs.subscaleview.ImageSource;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RunningAverageRssiFilter;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.domain.Localization;
import sk.beacode.beacodeapp.domain.Trilateration;
import sk.beacode.beacodeapp.domain.entity.BeaconEntity;
import sk.beacode.beacodeapp.domain.entity.PointEntity;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.managers.Manager;
import sk.beacode.beacodeapp.managers.NotificationManager;
import sk.beacode.beacodeapp.managers.SelectedExhibitsApi;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.Pin;
import sk.beacode.beacodeapp.models.Pixel;
import sk.beacode.beacodeapp.models.SelectedExhibit;
import sk.beacode.beacodeapp.models.SelectedExhibitList;
import sk.beacode.beacodeapp.views.PinView;

/**
 * Activity which shows a map with exhibits and user location.
 */
@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity implements ExhibitionDetailDialog.ExhibitDetailListener, BeaconConsumer {

    private int MAP_WIDTH = 10521;
    private int MAP_HEIGHT = 1627;

    private static Event event;

    public static Event getEvent() {
        return event;
    }

    public static void setEvent(Event event) {
        NavigationActivity.event = event;
    }

    private boolean eventDialogOpened = false;
    private PointEntity lastLocation;

//    private Beacon beaconInterface;

    @BindView(R.id.map)
    ImageView mapView;

    private BeaconManager beaconManager;
    private final Localization localization = Localization.getInstance();

    private Set<Integer> exhibitsWithSentFeedback = new HashSet<>();

    private boolean shouldDisplayFeedbackDialog(Integer exhibitId) {
        return !exhibitsWithSentFeedback.contains(exhibitId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //ButterKnife.bind(this);

        Intent intent = getIntent();
        int exhibitId = intent.getIntExtra("exhibitId", -1);
        if (exhibitId != -1) {
            for (Category c : event.getCategories()) {
                for (Exhibit e : c.getExhibits()) {
                    if (e.getId() == exhibitId) {
                        showExhibitDialog(e);
                        break;
                    }
                }
            }
        }

        downloadMap(event.getMap().getUri());

        //RunningAverageRssiFilter.setSampleExpirationMilliseconds(100);
        BeaconManager.setRssiFilterImplClass(RunningAverageRssiFilter.class);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setBackgroundScanPeriod(200);
        beaconManager.setBackgroundBetweenScanPeriod(0);
        beaconManager.setForegroundScanPeriod(200);
        beaconManager.setForegroundBetweenScanPeriod(0);
        beaconManager.bind(this);
//
//        try {
//            beaconManager.updateScanPeriods();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

        // Any implementation of ImageView can be used!
//        ImageView imageView = (ImageView) findViewById(R.id.map);
//
//         mAttacher = new PhotoViewAttacher(imageView);

//        final Localization localization = Localization.getInstance();

//        beaconInterface = BeaconFactory.getBeacon(this, BeaconFactory.BeaconType.GIMBAL);
//        beaconInterface.addBeaconListener(new BeaconListener() {
//            @Override
//            public void onAllBeacons(Collection<BeaconEntity> beacons) {
//                System.out.println("XXX" + beacons.size());
//                for (BeaconEntity e : beacons) {
//                    sk.beacode.beacodeapp.models.Beacon b = event.getBeacon(e.getMinor());
//                    double x;
//                    double y;
//                    if (b != null) {
//                        System.out.println("XXX" + b.getMinor());
//                        x = b.getX();
//                        y = b.getY();
//                    } else if (e.getMinor() == 6) {
//                        x = 0.60;
//                        y = 0;
//                    } else if (e.getMinor() == 5) {
//                        x = 3.45;
//                        y = 0;
//                    } else if (e.getMinor() == 3) {
//                        x = 3.45;
//                        y = 3.45;
//                    } else {
//                        x = 0;
//                        y = 3.45;
//                    }
//                    e.setPosition(new PointEntity(x, y));
//                }
//
//                List<BeaconEntity> beaconList = new ArrayList<>(beacons);
//                if (beaconList.size() >= 2) {
//                    localization.updateUserPosition(beaconList);
//                }
//
//                PointEntity userPosition = localization.getUserPosition();
//                System.out.println("x: " + userPosition.getX());
//                System.out.println("y: " + userPosition.getY());
//                updatePosition(userPosition.getX(), userPosition.getY());
//            }
//
//            @Override
//            public void onNearestBeacon(BeaconEntity beacon) {
//
//            }
//        });
    }

//    @Override
//    public void onBeaconServiceConnect() {
//        manager.addRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                if (beacons.size() == 0) {
//                    return;
//                }
//
//                Beacon closest = Collections.min(beacons, new Comparator<Beacon>() {
//                    @Override
//                    public int compare(Beacon beacon, Beacon t1) {
//                        double dist1 = beacon.getDistance();
//                        double dist2 = t1.getDistance();
//                        if (dist1 < dist2) {
//                            return -1;
//                        } else if (dist1 == dist2) {
//                            return 0;
//                        } else {
//                            return 1;
//                        }
//                    }
//                });
//
//                String uuid = closest.getId1().toString();
//                int major = closest.getId2().toInt();
//                int minor = closest.getId3().toInt();
//                double distance = closest.getDistance();
//
//                System.out.println(uuid);
//                System.out.println(major);
//                System.out.println(minor);
//                System.out.println(distance);
//
//                updateDistance(minor, distance);
//
//                if (distance <= 1.0) {
//                    for (Exhibit e : event.getExhibits()) {
//                        if (e.getBeacons() != null) {
//                            for (sk.beacode.beacodeapp.models.Beacon b : e.getBeacons()) {
//                                System.out.println(b.getUuid());
//                                System.out.println(b.getMajor());
//                                System.out.println(b.getMinor());
//                                if (!eventDialogOpened) {
//                                    showExhibit(e);
//                                }
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
//        try {
//            manager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }

    @Background
    void showExhibit(Exhibit exhibit) {
        exhibit.getImages();
        showExhibitDialog(exhibit);
    }

    @UiThread
    void showExhibitDialog(Exhibit exhibit) {
        eventDialogOpened = true;
        ExhibitionDetailDialog dialog = new ExhibitionDetailDialog();
        dialog.setListener(this);
        dialog.bind(exhibit);
        try {
            dialog.show(getFragmentManager(), "");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update user position.
     * @param x - user position
     * @param y - user position
     */
    @UiThread
    void updatePosition(double x, double y) {
        PinView mapView = (PinView) findViewById(R.id.map);
        if (x < 0) {
            x = 0;
        }
        if (x > MAP_WIDTH) {
            x = MAP_WIDTH;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > MAP_HEIGHT) {
            y = MAP_HEIGHT;
        }
        mapView.setUserPosition(new Pixel((int) x, (int) y));

        TextView textX = (TextView) findViewById(R.id.x);
        TextView textY = (TextView) findViewById(R.id.y);
        textX.setText(Double.toString(x));
        textY.setText(Double.toString(y));

    }

    @Override
    public void onExhibitionDetailClose() {
        eventDialogOpened = false;
    }

    @Background
    public void downloadMap(Uri mapUri) {
        try {
            URL url = new URL(mapUri.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader br = new BufferedReader( new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            SVG svg = SVG.getFromString(sb.toString());

            showMap(svg);

        } catch (SVGParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void showExhibitDetail(Exhibit exhibit) {
        if (exhibit == null) {
            new MaterialDialog.Builder(NavigationActivity.this)
                    .title("No exhibit")
                    .show();
            return;
        }

        new MaterialDialog.Builder(NavigationActivity.this)
                .title(exhibit.getName())
                .content(exhibit.getDescription())
                .show();
    }

    /**
     * Return if x is from interval <a - b, a + b>
     * @param n
     * @param a
     * @param b
     * @return
     */
    private boolean isFromInterval(int n, int a, int b) {
        return n >= a - b && n <= a + b;
    }

    @UiThread
    public void showMap(SVG map) {
        final PinView mapView = (PinView) findViewById(R.id.map);

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mapView.isReady()) {
                    PointF sCoord = mapView.viewToSourceCoord(e.getX(), e.getY());
                    for (Pin pin : mapView.getPins()) {
                        int xC = (int) pin.getLocation().x;
                        int yC = (int) pin.getLocation().y;
                        int xP = (int) sCoord.x;
                        int yP = (int) sCoord.y;
                        if (isFromInterval(xC, xP, 100) && isFromInterval(yC, yP, 100)) {
                            Integer exhibitId = event.getBeacon(pin.getMinor()).getExhibitId();
                            if (exhibitId != null) {
                                for (Category cat : event.getCategories()) {
                                    for (Exhibit ex : cat.getExhibits()) {
                                        if (ex.getId() == exhibitId) {
                                            showExhibitDialog(ex);
                                            return true;
                                        }
                                    }
                                }
                            } else {
                                showExhibitDetail(null);
                                return true;
                            }
                        }
                    }
                }
                return true;
            }
        });

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        Bitmap newBM = Bitmap.createBitmap((int) Math.ceil(map.getDocumentWidth()),
                                           (int) Math.ceil(map.getDocumentHeight()),
                                           Bitmap.Config.ARGB_8888);

        Canvas bmcanvas = new Canvas(newBM);

        bmcanvas.drawRGB(255, 255, 255);

        map.renderToCanvas(bmcanvas);

        mapView.setImage(ImageSource.bitmap(newBM));
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        SelectedExhibitsApi api = Manager.getInstance().getSelectedExhibitsApi();
        Call<SelectedExhibitList> call = api.getSelectedExhibits(EventActivity_.getEvent().getId());
        call.enqueue(new Callback<SelectedExhibitList>() {
            @Override
            public void onResponse(Call<SelectedExhibitList> call, Response<SelectedExhibitList> response) {
                for (sk.beacode.beacodeapp.models.Beacon beacon : event.getBeacons()) {
                    // TODO
                    Pin.Color color = response.body().getSelectedExhibits().size() == 0 ? Pin.Color.GREEN : Pin.Color.BLUE;

                    for (SelectedExhibit s : response.body().getSelectedExhibits()) {
                        if (beacon.getExhibitId() != null && s.getExhibit().getId() == beacon.getExhibitId()) {
                            color = Pin.Color.GREEN;
                            break;
                        }
                    }

                    addPin(beacon.getX(), beacon.getY(), color, beacon.getMinor());
                }
            }

            @Override
            public void onFailure(Call<SelectedExhibitList> call, Throwable t) {
                for (sk.beacode.beacodeapp.models.Beacon beacon : event.getBeacons()) {
                    addPin(beacon.getX(), beacon.getY(), Pin.Color.BLUE, beacon.getMinor());
                }
            }
        });
    }

    /**
     * Add pin onto specified position on the map.
     * @param x
     * @param y
     * @param color
     * @param minor
     */
    @DebugLog
    public void addPin(double x, double y, Pin.Color color, int minor) {
        if (minor < 68) {
            return;
        }
        PinView mapView = (PinView) findViewById(R.id.map);
        mapView.addPin(new Pixel((int) (x * 1.6), (int) (y * 1.55)), color, minor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public void showFeedbackDialog(Exhibit exhibit) {
        new MaterialDialog.Builder(this)
                .title(exhibit.getName())
                .customView(R.layout.dialog_feedback, false)
                .positiveText("Rate")
                .show();
    }

    /**
     * Beacon listener
     */
    @Override
    public void onBeaconServiceConnect() {
        try {
            beaconManager.updateScanPeriods();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            @DebugLog
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                final List<BeaconEntity> sortedBeacons = new ArrayList<>();
                System.out.println(sortedBeacons.size());
                for (Beacon b : beacons) {
                    double d0_rssi = -74;
                    double n = 4;
                    double rssi = b.getRssi();
                    double distance = Math.pow(10.0, (d0_rssi - rssi) / (10.0 * n));

                    Log.i("xxx", String.format("%s: %d %f %f", b.getId3(), b.getRssi(), b.getDistance(), distance));

                    BeaconEntity beaconEntity = new BeaconEntity();
                    beaconEntity.setMinor(b.getId3().toInt());
                    beaconEntity.setDistance(b.getDistance());

                    sk.beacode.beacodeapp.models.Beacon beaconApi = event.getBeacon(beaconEntity.getMinor());
                    double x;
                    double y;
                    if (beaconApi != null) {
                        x = beaconApi.getX() * 1.6 / 100;
                        y = beaconApi.getY() * 1.55 / 100;
                    }
                    else {
                        continue;
                    }
//                    else if (beaconEntity.getMinor() == 6) {
//                        x = 0.60;
//                        y = 0;
//                    } else if (beaconEntity.getMinor() == 5) {
//                        x = 3.45;
//                        y = 0;
//                    } else if (beaconEntity.getMinor() == 3) {
//                        x = 3.45;
//                        y = 3.45;
//                    } else {
//                        x = 0;
//                        y = 3.45;
//                    }

                    beaconEntity.setPosition(new PointEntity(x, y));
                    sortedBeacons.add(beaconEntity);
                }

                Collections.sort(sortedBeacons, new Comparator<BeaconEntity>() {
                    @Override
                    public int compare(BeaconEntity beacon, BeaconEntity t1) {
                        return Double.valueOf(beacon.getDistance()).compareTo(t1.getDistance());
                    }
                });

                if (sortedBeacons.size() < 3) {
                    return;
                }

                BeaconEntity b0 = sortedBeacons.get(0);
                BeaconEntity b1 = sortedBeacons.get(1);
                BeaconEntity b2 = sortedBeacons.get(2);

                PointEntity position = Trilateration.getLocation(b0.getPosition(), b1.getPosition(), b2.getPosition(),
                        b0.getDistance(), b1.getDistance(), b2.getDistance());

                if ((Double.isNaN(position.getX()) || Double.isInfinite(position.getX())
                        || Double.isNaN(position.getY()) || Double.isInfinite(position.getY())) && lastLocation != null) {
                    position = lastLocation;
                }

                lastLocation = position;

                System.out.println(String.format("position: x = %f, y = %f", position.getX() * 100, position.getY() * 100));
                updatePosition(position.getX() * 100, position.getY() * 100);

                SelectedExhibitsApi api = Manager.getInstance().getSelectedExhibitsApi();
                Call<SelectedExhibitList> call = api.getSelectedExhibits(event.getId());
                call.enqueue(new Callback<SelectedExhibitList>() {
                    @Override
                    @DebugLog
                    public void onResponse(Call<SelectedExhibitList> call, Response<SelectedExhibitList> response) {
                        for (BeaconEntity e : sortedBeacons) {
                            if (e.getDistance() > 2.5) {
                                Integer exhibitId = event.getBeacon(e.getMinor()).getExhibitId();
                                //Integer exhibitId = 6;
                                for (SelectedExhibit s : response.body().getSelectedExhibits()) {
                                    if (exhibitId != null && s.getExhibit().getId() == exhibitId) {
                                        //NotificationManager.getInstance().showNotification(NavigationActivity.this, NavigationActivity_.class, s.getExhibit().getName(), s.getExhibit().getDescription());
                                        NotificationManager notificationManager = NotificationManager.getInstance();
                                        if (!notificationManager.shouldDisplayNotification(exhibitId)
                                                && shouldDisplayFeedbackDialog(exhibitId)) {
                                            showFeedbackDialog(s.getExhibit());
                                            exhibitsWithSentFeedback.add(exhibitId);
                                            //notificationManager.showNotification(NavigationActivity.this, NavigationActivity_.class, exhibitId, Integer.toString(e.getMajor()), Integer.toString(e.getMinor()));
                                            //notificationManager.showNotification(NavigationActivity.this, NavigationActivity_.class, exhibitId, s.getExhibit().getName(), s.getExhibit().getDescription());
                                        }

                                    }
                                }
//                                FeedbackDialog dialog = new FeedbackDialog();
//                                String feedback = dialog.getInput();
//                                boolean positive = dialog.isInputPositive();
//
//                                FeedbackStateContext c = new FeedbackStateContext();
//
//                                FeedbackState state;
//                                if (positive) {
//                                    state = new FeedbackPositiveState();
//                                } else {
//                                    state = new FeedbackNegativeState();
//                                }
//
//                                c.setFeedback(feedback);
//                                c.setState(state);
//
//                                state.submit(c);

                                break;
                            }

                            if (e.getDistance() > 1.5) {
                                break;
                            }

                            Integer exhibitId = event.getBeacon(e.getMinor()).getExhibitId();
                            //Integer exhibitId = 6;
                            for (SelectedExhibit s : response.body().getSelectedExhibits()) {
                                if (exhibitId != null && s.getExhibit() != null && s.getExhibit().getId() == exhibitId) {
                                    //NotificationManager.getInstance().showNotification(NavigationActivity.this, NavigationActivity_.class, s.getExhibit().getName(), s.getExhibit().getDescription());
                                    NotificationManager notificationManager = NotificationManager.getInstance();
                                    if (notificationManager.shouldDisplayNotification(exhibitId)) {
                                        //notificationManager.showNotification(NavigationActivity.this, NavigationActivity_.class, exhibitId, Integer.toString(e.getMajor()), Integer.toString(e.getMinor()));
                                        notificationManager.showNotification(NavigationActivity.this, NavigationActivity_.class, exhibitId, s.getExhibit().getName(), s.getExhibit().getDescription());
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectedExhibitList> call, Throwable t) {

                    }
                });
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
