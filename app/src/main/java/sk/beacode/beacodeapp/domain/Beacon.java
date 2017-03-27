package sk.beacode.beacodeapp.domain;

import java.util.Collection;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;


public interface Beacon {
    Collection<BeaconEntity> getAllBeaconsInRange(Collection<org.altbeacon.beacon.Beacon> beacons);
    BeaconEntity getNearestBeacon(Collection<org.altbeacon.beacon.Beacon> beacons);
    String getLayout();
    void addBeaconListener(BeaconListener listener);
    void unbind();
}
