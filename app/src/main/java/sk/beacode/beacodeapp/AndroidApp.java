package sk.beacode.beacodeapp;

import android.app.Application;

public class AndroidApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

//		IBeaconScanner.initialize(IBeaconScanner.newInitializer(this).build());
	}
}
