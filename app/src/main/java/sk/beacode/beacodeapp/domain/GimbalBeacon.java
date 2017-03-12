package sk.beacode.beacodeapp.domain;

import android.content.Context;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.service.RangedBeacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;


public class GimbalBeacon extends BaseBeacon {

    public GimbalBeacon(Context context) {
        super(context);
        RangedBeacon.setSampleExpirationMilliseconds(1000);
    }

    @Override
    public Collection<BeaconEntity> getAllBeaconsInRange(Collection<org.altbeacon.beacon.Beacon> beacons) {
        List<BeaconEntity> allBeacons = new ArrayList<>(beacons.size());
        for (Beacon b : beacons) {
            System.out.println("id: " + b.getId3().toInt() + ", rssi: " + b.getRssi());
            BeaconEntity beaconEntity = new BeaconEntity();

//            double rd0 = -74.0;
//            double n = 2.25;
//            double exp = (b.getTxPower() - b.getRssi()) / (10.0 * n);
//            System.out.println((b.getRssi() - rd0));
//            System.out.println((10.0 * n));
//            double d = Math.pow(10.0, exp);
//            System.out.println("DDDDDDDDDDDDDDDDD:" + d);
//            beaconEntity.setDistance(d);

//            Beacon.setDistanceCalculator(new CurveFittedDistanceCalculator(1.33, 5.68, 0.0));
//            System.out.println(Beacon.getDistanceCalculator());

            beaconEntity.setDistance(b.getDistance());
            beaconEntity.setUuid(b.getId1().toString());
            beaconEntity.setMajor(b.getId2().toInt());
            beaconEntity.setMinor(b.getId3().toInt());
            allBeacons.add(beaconEntity);
        }

        System.out.println("----------------------");

        return allBeacons;
    }

    @Override
    public BeaconEntity getNearestBeacon(Collection<org.altbeacon.beacon.Beacon> beacons) {
        if (beacons.size() == 0) {
            return null;
        }

        org.altbeacon.beacon.Beacon closest = Collections.min(beacons, new Comparator<org.altbeacon.beacon.Beacon>() {
            @Override
            public int compare(org.altbeacon.beacon.Beacon beacon, org.altbeacon.beacon.Beacon t1) {
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

        BeaconEntity beaconEntity = new BeaconEntity();
        beaconEntity.setDistance(closest.getDistance());
        beaconEntity.setUuid(closest.getId1().toString());
        beaconEntity.setMajor(closest.getId2().toInt());
        beaconEntity.setMinor(closest.getId3().toInt());

        return beaconEntity;
    }

    @Override
    public String getLayout() {
        return "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24";
    }
}
