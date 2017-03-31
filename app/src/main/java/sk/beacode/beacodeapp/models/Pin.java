package sk.beacode.beacodeapp.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import hugo.weaving.DebugLog;
import sk.beacode.beacodeapp.R;

/**
 * Created by Veronika on 19.03.2017.
 */

public class Pin {

    public enum Color {
        BLUE,
        RED,
    }

    private PointF location;
    private Color color;
    private Bitmap bitmap;
    private int minor;

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
    }

    @DebugLog
    public Bitmap getBitmap(Context context) {
        if (bitmap == null) {
            switch (color) {
                case BLUE:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_icon_blue);
                    break;
                case RED:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_icon_red);
                    break;
                default:
                    bitmap = null;
                    break;
            }
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
