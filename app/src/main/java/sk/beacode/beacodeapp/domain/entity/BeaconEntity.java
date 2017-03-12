package sk.beacode.beacodeapp.domain.entity;

/**
 * Created by Sandra on 25.02.2017.
 */

public class BeaconEntity {
    private String uuid;
    private int major;
    private int minor;
    private double distance;
    private PointEntity position;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public PointEntity getPosition() {
        return position;
    }

    public void setPosition(PointEntity position) {
        this.position = position;
    }
}
