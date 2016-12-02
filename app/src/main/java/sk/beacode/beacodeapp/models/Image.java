package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.URL;

import sk.beacode.beacodeapp.managers.Manager;

public class Image implements Parcelable {
    private static final String TAG = "Image";

    private int id;
    private String description;
    private String pathWithFile;
    private Bitmap bitmap;

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel parcel) {
            return new Image(parcel);
        }

        @Override
        public Image[] newArray(int i) {
            return new Image[i];
        }
    };

    public Image() {}

    private Image(Parcel parcel) {
        id = parcel.readInt();
        description = parcel.readString();
        pathWithFile = parcel.readString();
        //bitmap = parcel.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(pathWithFile);
        //bitmap.writeToParcel(parcel, i);
    }

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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
