package sk.beacode.beacodeapp.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sk.beacode.beacodeapp.domain.entity.BeaconEntity;
import sk.beacode.beacodeapp.domain.entity.PointEntity;


public class Localization {

    private static final int N = 3;
    private static final Localization instance = new Localization();
    private PointEntity userPosition = new PointEntity(0, 0);

    private Localization() {}

    public static Localization getInstance() {
        return instance;
    }

    private static List<BeaconEntity> getNNearestBeacons(List<BeaconEntity> beacons, int n) {
        Collections.sort(beacons, new Comparator<BeaconEntity>() {
            @Override
            public int compare(BeaconEntity beaconEntity, BeaconEntity t1) {
                return Double.valueOf(beaconEntity.getDistance()).compareTo(t1.getDistance());
            }
        });

        List<BeaconEntity> nearestBeacons = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nearestBeacons.add(beacons.get(i));
        }

        return nearestBeacons;
    }

    public PointEntity getUserPosition() {
        return userPosition;
    }

    public void updateUserPosition(List<BeaconEntity> beacons) {
        if (beacons.size() < N) {
            return;
        }

        List<BeaconEntity> nearest = getNNearestBeacons(beacons, N);
        userPosition = Trilateration.getLocation(nearest.get(0).getPosition(), nearest.get(1).getPosition(), nearest.get(2).getPosition(),
                nearest.get(0).getDistance(), nearest.get(1).getDistance(), nearest.get(2).getDistance());

//        double[][] positions = new double[beacons.size()][];
//        double[] distances = new double[beacons.size()];
//        for (int i = 0; i < beacons.size(); i++) {
//            positions[i] = beacons.get(i).getPosition();
//            distances[i] = beacons.get(i).getDistance() * 10;
//            System.out.println("x:" + beacons.get(i).getPosition()[0]);
//            System.out.println("y:" + beacons.get(i).getPosition()[1]);
//            System.out.println("d:" + beacons.get(i).getDistance());
//        }
//
//        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
//                new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
//        LeastSquaresOptimizer.Optimum optimum = solver.solve(true);

//        userPosition = optimum.getPoint().toArray();
    }
}
