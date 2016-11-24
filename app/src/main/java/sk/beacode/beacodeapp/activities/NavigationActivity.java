package sk.beacode.beacodeapp.activities;


import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RangedBeacon;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.beacode.beacodeapp.R;


@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity implements BeaconConsumer {

    private static final String[] LAYOUTS = {
            "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24",
    };

    Map<Identifier, List<Double>> distances = new HashMap<>();
    private BeaconManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = BeaconManager.getInstanceForApplication(this);
        RangedBeacon.setSampleExpirationMilliseconds(1000);
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

                Map<Identifier, Double> avgDistances = new HashMap<>();
                for (Beacon beacon : beacons) {
                    if (!distances.containsKey(beacon.getId3())) {
                        distances.put(beacon.getId3(), new ArrayList<Double>());
                    } else if (distances.get(beacon.getId3()).size() >= 1) {
                        distances.get(beacon.getId3()).remove(0);
                    }
                    distances.get(beacon.getId3()).add(beacon.getDistance());

                    double avg = 0;
                    for (Double d : distances.get(beacon.getId3())) {
                        avg += d;
                    }
                    avg /= distances.get(beacon.getId3()).size();
                    avgDistances.put(beacon.getId3(), avg);
                }

                Map.Entry<Identifier, Double> minDistance = null;
                for (Map.Entry<Identifier, Double> e : avgDistances.entrySet()) {
                    if (minDistance == null || e.getValue() < minDistance.getValue()) {
                        minDistance = e;
                    }
                }

//                Beacon closest = Collections.min(beacons, new Comparator<Beacon>() {
//                    @Override
//                    public int compare(Beacon beacon, Beacon t1) {
//                        double dist1 = beacon.getDistance();
//                        double dist2 = t1.getDistance();
//
//                        if (dist1 > dist2) {
//                            return 1;
//                        } else if (dist1 == dist2) {
//                            return 0;
//                        } else {
//                            return -1;
//                        }
//                    }
//                });

                System.out.println(minDistance.getKey());
                updateDistance(minDistance.getKey(), minDistance.getValue());

                //if (closest.getDistance() <= 1.0) {
                //    Toast.makeText(NavigationActivity.this, "Beacon is close", Toast.LENGTH_SHORT).show();
                //}
            }
        });

        try {
            manager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateDistance(Identifier id, double distance) {
        DecimalFormat df = new DecimalFormat("##.#");
        TextView distanceView = (TextView) findViewById(R.id.distance);
        distanceView.setText(id.toString() + ":  " + df.format(distance) + "m");
    }
}
