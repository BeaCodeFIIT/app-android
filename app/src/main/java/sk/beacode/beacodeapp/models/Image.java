package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.URL;

import sk.beacode.beacodeapp.managers.Manager;

public class Image {
    private static final String TAG = "Image";

    private int id;
    private String description;
    private String pathWithFile;
    private Bitmap bitmap;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPathWithFile() {
        return pathWithFile;
    }

    public Bitmap getBitmap() {
        if (bitmap != null) {
            return bitmap;
        }

        try {
            URL url = new URL(Manager.IMAGE_ROOT_URL + pathWithFile);
            bitmap = BitmapFactory.decodeStream(url.openStream());
            return bitmap;
        } catch (java.io.IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
