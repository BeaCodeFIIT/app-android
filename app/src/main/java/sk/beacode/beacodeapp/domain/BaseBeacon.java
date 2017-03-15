package sk.beacode.beacodeapp.domain;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RunningAverageRssiFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public abstract class BaseBeacon implements Beacon, BeaconConsumer {

    private BeaconManager manager;
    private Context context;
    private List<BeaconListener> listeners = new ArrayList<>();

    public BaseBeacon(Context context) {
        this.context = context;

        BeaconManager.setRssiFilterImplClass(RunningAverageRssiFilter.class);
//        RunningAverageRssiFilter.setSampleExpirationMilliseconds(1000);
        manager = BeaconManager.getInstanceForApplication(context);
//        manager.setForegroundScanPeriod(1000);
//        manager.setForegroundBetweenScanPeriod(0);
        manager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(getLayout()));
        manager.bind(this);
    }

    public BeaconManager getManager() {
        return manager;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return context.bindService(intent, serviceConnection, i);
    }

    @Override
    public void onBeaconServiceConnect() {
        manager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> beacons, Region region) {
                for (BeaconListener listener : listeners) {
                    listener.onAllBeacons(getAllBeaconsInRange(beacons));
                    listener.onNearestBeacon(getNearestBeacon(beacons));
                }
            }
        });

        try {
            manager.startRangingBeaconsInRegion(new Region(UUID.randomUUID().toString(), null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBeaconListener(BeaconListener listener) {
        listeners.add(listener);
    }
}
