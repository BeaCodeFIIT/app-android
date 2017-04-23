package sk.beacode.beacodeapp.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;

import hugo.weaving.DebugLog;

/**
 * Created by Veronika on 19.03.2017.
 */

public class Pin {

    public enum Color {
        BLUE,
        RED,
        GREEN,
    }

    private PointF location;
    private Color color;
    private Bitmap bitmap;
    private int minor;
    private BitmapApi bitmapApi;

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        switch (color) {
            case RED:
                bitmapApi = new RedBitmaps();
                break;
            case GREEN:
                bitmapApi = new GreenBitmaps();
                break;
            case BLUE:
                bitmapApi = new BlueBitmaps();
                break;
            default:
                bitmapApi = null;
                break;
        }
    }

    @DebugLog
    public Bitmap getBitmap(Context context) {
        if (bitmap == null) {
            bitmap = bitmapApi.getPin(context);
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}
