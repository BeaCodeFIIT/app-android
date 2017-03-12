package sk.beacode.beacodeapp.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.domain.Beacon;
import sk.beacode.beacodeapp.domain.BeaconFactory;
import sk.beacode.beacodeapp.domain.BeaconListener;
import sk.beacode.beacodeapp.domain.Localization;
import sk.beacode.beacodeapp.domain.entity.BeaconEntity;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;


@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity implements ExhibitionDetailDialog.ExhibitDetailListener {

    public static Event event;

    private boolean eventDialogOpened = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Localization localization = Localization.getInstance();

        Beacon beaconInterface = BeaconFactory.getBeacon(this, BeaconFactory.BeaconType.GIMBAL);
        beaconInterface.addBeaconListener(new BeaconListener() {
            @Override
            public void onAllBeacons(Collection<BeaconEntity> beacons) {
                for (BeaconEntity entity : beacons) {
                    double x = 0;
                    double y = 0;
                    if (entity.getMinor() == 6) {
                        x = 0.6;
                        y = 0;
                    } else if (entity.getMinor() == 5) {
                        x = 3.45;
                        y = 0;
                    } else if (entity.getMinor() == 3) {
                        x = 3.45;
                        y = 3.45;
                    }
                    entity.setPosition(x, y);
                }

                List<BeaconEntity> beaconList = new ArrayList<>(beacons);
                if (beaconList.size() >= 2) {
                    localization.updateUserPosition(beaconList);
                }
                System.out.println("x: " + localization.getUserPosition()[0]);
                System.out.println("y: " + localization.getUserPosition()[1]);
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
    void updateDistance(int id, double distance) {
        DecimalFormat df = new DecimalFormat("##.#");
        TextView distanceView = (TextView) findViewById(R.id.distance);
        distanceView.setText(id + ":  " + df.format(distance) + "m");
    }

    @Override
    public void onExhibitionDetailClose() {
        eventDialogOpened = false;
    }
}
