package sk.beacode.beacodeapp.domain;

import java.util.Collection;
import java.util.List;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;


public interface Beacon {
    public Collection<BeaconEntity> getAllBeaconsInRange(Collection<org.altbeacon.beacon.Beacon> beacons);
    public BeaconEntity getNearestBeacon(Collection<org.altbeacon.beacon.Beacon> beacons);
    public String getLayout();
    public void addBeaconListener(BeaconListener listener);
}
