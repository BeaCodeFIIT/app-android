package sk.beacode.beacodeapp.domain;

import java.util.Collection;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;
import sk.beacode.beacodeapp.models.Beacon;


public interface BeaconListener {
    public void onAllBeacons(Collection<BeaconEntity> beacons);
    public void onNearestBeacon(BeaconEntity beacon);
}
