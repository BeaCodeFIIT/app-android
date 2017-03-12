package sk.beacode.beacodeapp.domain;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.List;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;

/**
 * Created by Sandra on 11.03.2017.
 */

public class Localization {

    private static final Localization instance = new Localization();
    private double[] userPosition = new double[2];

    private Localization() {}

    public static Localization getInstance() {
        return instance;
    }

    public double[] getUserPosition() {
        return userPosition;
    }

    public void updateUserPosition(List<BeaconEntity> beacons) {
        double[][] positions = new double[beacons.size()][];
        for (int i = 0; i < beacons.size(); i++) {
            positions[i] = beacons.get(i).getPosition();
        }

        double[] distances = new double[beacons.size()];
        for (int i = 0; i < beacons.size(); i++) {
            distances[i] = beacons.get(i).getDistance();
        }

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
                new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        userPosition = optimum.getPoint().toArray();
    }
}
