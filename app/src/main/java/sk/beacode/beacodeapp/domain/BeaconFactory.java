package sk.beacode.beacodeapp.domain;

import android.content.Context;


public class BeaconFactory {

    public enum BeaconType {
        GIMBAL,
        IBEACON,
    }

    private BeaconFactory() {}

    public static Beacon getBeacon(Context context, BeaconType beaconType) {
        switch (beaconType) {
            case GIMBAL:
                return new GimbalBeacon(context);
            case IBEACON:
                return new IBeaconBeacon(context);
            default:
                return null;
        }
    }
}
