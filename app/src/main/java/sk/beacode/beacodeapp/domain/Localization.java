package sk.beacode.beacodeapp.domain;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandra on 11.03.2017.
 */

public class Localization {

    private static final Localization instance = new Localization();

    private final List<double[]> positions = new ArrayList<>();
    private final List<Double> distances = new ArrayList<>();
    private double[] userPosition = new double[2];

    private Localization() {}

    public static Localization getInstance() {
        return instance;
    }

    public int addBeacon(double x, double y) {
        positions.add(new double[] {x, y});
        distances.add(.0);
        return positions.size() - 1;
    }

    public void removeAllBeacons() {
        positions.clear();
    }

    public void setDistance(int id, double distance) {
        distances.set(id, distance);
    }

    public double[] getUserPosition() {
        return userPosition;
    }

    public void updateUserPosition() {
        double[][] positions = this.positions.toArray(new double[this.positions.size()][]);
        double[] distances = ArrayUtils.toPrimitive(this.distances.toArray(new Double[this.distances.size()]));

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
                new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        userPosition = optimum.getPoint().toArray();
    }
}
