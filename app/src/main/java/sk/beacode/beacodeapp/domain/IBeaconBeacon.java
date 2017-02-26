package sk.beacode.beacodeapp.domain;

import android.content.Context;

import java.util.Collection;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;


public class IBeaconBeacon extends BaseBeacon {

    public IBeaconBeacon(Context context) {
        super(context);
    }

    @Override
    public Collection<BeaconEntity> getAllBeaconsInRange(Collection<org.altbeacon.beacon.Beacon> beacons) {
        return null;
    }

    @Override
    public BeaconEntity getNearestBeacon(Collection<org.altbeacon.beacon.Beacon> beacons) {
        return null;
    }

    @Override
    public String getLayout() {
        return "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
    }
}
