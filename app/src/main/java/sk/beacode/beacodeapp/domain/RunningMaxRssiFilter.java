package sk.beacode.beacodeapp.domain;

import org.altbeacon.beacon.logging.LogManager;
import org.altbeacon.beacon.service.RssiFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class RunningMaxRssiFilter implements RssiFilter {

	private static final String TAG = "RunningAverageRssiFilter";
	public static final long DEFAULT_SAMPLE_EXPIRATION_MILLISECONDS = 20000; /* 20 seconds */
	private static long sampleExpirationMilliseconds = DEFAULT_SAMPLE_EXPIRATION_MILLISECONDS;
	private ArrayList<Measurement> mMeasurements = new ArrayList<>();

	public void addMeasurement(Integer rssi) {
		Measurement measurement = new Measurement();
		measurement.rssi = rssi;
		measurement.timestamp = new Date().getTime();
		mMeasurements.add(measurement);
	}

	public boolean noMeasurementsAvailable() {
		return mMeasurements.size() == 0;
	}

	public double calculateRssi() {
		refreshMeasurements();
		int size = mMeasurements.size();
		int startIndex = 0;
		int endIndex = size -1;
		if (size > 2) {
			startIndex = size/10+1;
			endIndex = size-size/10-2;
		}

		double max = -999999;

		@SuppressWarnings("unused")
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {

			if(mMeasurements.get(i).rssi > max)
				max = mMeasurements.get(i).rssi;
			sum += mMeasurements.get(i).rssi;
		}


		//double runningAverage = sum/(endIndex-startIndex+1);
		double runningAverage = max;

		LogManager.d(TAG, "Running average mRssi based on %s measurements: %s",
				size, runningAverage);
		return runningAverage;
	}

	private synchronized void refreshMeasurements() {
		Date now = new Date();
		ArrayList<Measurement> newMeasurements = new ArrayList<Measurement>();
		Iterator<Measurement> iterator = mMeasurements.iterator();
		while (iterator.hasNext()) {
			Measurement measurement = iterator.next();
			if (now.getTime() - measurement.timestamp < sampleExpirationMilliseconds ) {
				newMeasurements.add(measurement);
			}
		}
		mMeasurements = newMeasurements;
		Collections.sort(mMeasurements);
	}

	private class Measurement implements Comparable<Measurement> {
		Integer rssi;
		long timestamp;
		@Override
		public int compareTo(Measurement arg0) {
			return rssi.compareTo(arg0.rssi);
		}
	}

	public static void setSampleExpirationMilliseconds(long newSampleExpirationMilliseconds) {
		sampleExpirationMilliseconds = newSampleExpirationMilliseconds;
	}
}
