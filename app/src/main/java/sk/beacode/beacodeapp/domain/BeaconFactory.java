package sk.beacode.beacodeapp.domain;

import android.content.Context;


public class BeaconFactory {

    private BeaconFactory() {}

    public static Beacon getBeacon(Context context, String beaconType) {
        switch (beaconType) {
            case "GIMBAL":
                return new GimbalBeacon(context);
            case "IBEACON":
                return new IBeaconBeacon(context);
            default:
                return new NullBeacon(context);
        }
    }
}
