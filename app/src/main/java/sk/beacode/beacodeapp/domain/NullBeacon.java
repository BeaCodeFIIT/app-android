package sk.beacode.beacodeapp.domain;

import android.content.Context;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;

public class NullBeacon extends BaseBeacon {

	public NullBeacon(Context context) {
		super(context);
	}

	@Override
	public Collection<BeaconEntity> getAllBeaconsInRange(Collection<Beacon> beacons) {
		return null;
	}

	@Override
	public BeaconEntity getNearestBeacon(Collection<Beacon> beacons) {
		return null;
	}

	@Override
	public String getLayout() {
		return null;
	}

	@Override
	public boolean isNull() {
		return true;
	}
}
