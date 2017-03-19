package sk.beacode.beacodeapp.activities;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.domain.Beacon;
import sk.beacode.beacodeapp.domain.BeaconFactory;
import sk.beacode.beacodeapp.domain.BeaconListener;
import sk.beacode.beacodeapp.domain.Localization;
import sk.beacode.beacodeapp.domain.entity.BeaconEntity;
import sk.beacode.beacodeapp.domain.entity.PointEntity;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.Pin;
import sk.beacode.beacodeapp.models.Pixel;
import sk.beacode.beacodeapp.models.Point;
import sk.beacode.beacodeapp.views.PinView;


@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity implements ExhibitionDetailDialog.ExhibitDetailListener {

    public static Event event;

    private boolean eventDialogOpened = false;

    @BindView(R.id.map)
    ImageView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //ButterKnife.bind(this);

        downloadMap(event.getMap().getUri());

        // Any implementation of ImageView can be used!
//        ImageView imageView = (ImageView) findViewById(R.id.map);
//
//         mAttacher = new PhotoViewAttacher(imageView);

        final Localization localization = Localization.getInstance();

        Beacon beaconInterface = BeaconFactory.getBeacon(this, BeaconFactory.BeaconType.GIMBAL);
        beaconInterface.addBeaconListener(new BeaconListener() {
            @Override
            public void onAllBeacons(Collection<BeaconEntity> beacons) {
                System.out.println("XXX" + beacons.size());
                for (BeaconEntity e : beacons) {
                    sk.beacode.beacodeapp.models.Beacon b = event.getBeacon(e.getMinor());
                    double x;
                    double y;
                    if (b != null) {
                        System.out.println("XXX" + b.getMinor());
                        x = b.getX();
                        y = b.getY();
                    } else if (e.getMinor() == 6) {
                        x = 0.60;
                        y = 0;
                    } else if (e.getMinor() == 5) {
                        x = 3.45;
                        y = 0;
                    } else if (e.getMinor() == 3) {
                        x = 3.45;
                        y = 3.45;
                    } else {
                        x = 0;
                        y = 3.45;
                    }
                    e.setPosition(new PointEntity(x, y));
                }

                List<BeaconEntity> beaconList = new ArrayList<>(beacons);
                if (beaconList.size() >= 2) {
                    localization.updateUserPosition(beaconList);
                }

                PointEntity userPosition = localization.getUserPosition();
                System.out.println("x: " + userPosition.getX());
                System.out.println("y: " + userPosition.getY());
                updatePosition(userPosition.getX(), userPosition.getY());
            }

            @Override
            public void onNearestBeacon(BeaconEntity beacon) {

            }
        });
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

    @UiThread
    void updatePosition(double x, double y) {
        PinView mapView = (PinView) findViewById(R.id.map);
        mapView.setUserPosition(new Point(x, y));

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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void showMap(SVG map) {
        PinView mapView = (PinView) findViewById(R.id.map);

        Bitmap newBM = Bitmap.createBitmap((int) Math.ceil(map.getDocumentWidth()),
                                           (int) Math.ceil(map.getDocumentHeight()),
                                           Bitmap.Config.ARGB_8888);

        Canvas bmcanvas = new Canvas(newBM);

        bmcanvas.drawRGB(255, 255, 255);

        map.renderToCanvas(bmcanvas);

        mapView.setImage(ImageSource.bitmap(newBM));
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        for (sk.beacode.beacodeapp.models.Beacon beacon : event.getBeacons()) {
            addPin(beacon.getX(), beacon.getY(), Pin.Color.BLUE);
        }

    }

    public void addPin(double x, double y, Pin.Color color) {
        PinView mapView = (PinView) findViewById(R.id.map);
        mapView.addPin(new Point(x, y), color);
    }



}
