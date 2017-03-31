package sk.beacode.beacodeapp.models;

/**
 * Created by Veronika on 19.03.2017.
 */

public class Pixel {
    private int x;
    private int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Pixel setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Pixel setY(int y) {
        this.y = y;
        return this;
    }
}
