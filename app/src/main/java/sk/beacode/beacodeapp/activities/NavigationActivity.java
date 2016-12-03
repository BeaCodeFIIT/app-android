package sk.beacode.beacodeapp.activities;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RangedBeacon;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Exhibit;


@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity implements BeaconConsumer, ExhibitionDetailDialog.ExhibitDetailListener {

    private static final String[] LAYOUTS = {
            "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24",
    };

    private BeaconManager manager;

    public static Event event;

    private boolean eventDialogOpened = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RangedBeacon.setSampleExpirationMilliseconds(1000);

        manager = BeaconManager.getInstanceForApplication(this);
        for (String layout : LAYOUTS) {
            manager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(layout));
        }
        manager.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        manager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() == 0) {
                    return;
                }

                Beacon closest = Collections.min(beacons, new Comparator<Beacon>() {
                    @Override
                    public int compare(Beacon beacon, Beacon t1) {
                        double dist1 = beacon.getDistance();
                        double dist2 = t1.getDistance();
                        if (dist1 < dist2) {
                            return -1;
                        } else if (dist1 == dist2) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                });

                String uuid = closest.getId1().toString();
                int major = closest.getId2().toInt();
                int minor = closest.getId3().toInt();
                double distance = closest.getDistance();

                System.out.println(uuid);
                System.out.println(major);
                System.out.println(minor);
                System.out.println(distance);

                updateDistance(minor, distance);

                if (distance <= 1.0) {
                    for (Exhibit e : event.getExhibits()) {
                        if (e.getBeacons() != null) {
                            for (sk.beacode.beacodeapp.models.Beacon b : e.getBeacons()) {
                                System.out.println(b.getUuid());
                                System.out.println(b.getMajor());
                                System.out.println(b.getMinor());
                                if (!eventDialogOpened) {
                                    showExhibit(e);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        });

        try {
            manager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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
