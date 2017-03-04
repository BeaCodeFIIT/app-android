package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

import sk.beacode.beacodeapp.managers.Manager;

public class Image {
    private static final String IMAGE_ROOT_URL = "http://147.175.149.218/beacode_dev";

    private int id;
    private String description;
    private String pathWithFile;

//    public static final Creator<Image> CREATOR = new Creator<Image>() {
//        @Override
//        public Image createFromParcel(Parcel parcel) {
//            return new Image(parcel);
//        }
//
//        @Override
//        public Image[] newArray(int i) {
//            return new Image[i];
//        }
//    };

    public Image() {}

//    private Image(Parcel parcel) {
//        id = parcel.readInt();
//        description = parcel.readString();
//        pathWithFile = parcel.readString();
//        //bitmap = parcel.readParcelable(Bitmap.class.getClassLoader());
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(id);
//        parcel.writeString(description);
//        parcel.writeString(pathWithFile);
//        //bitmap.writeToParcel(parcel, i);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getUri() {
        return Uri.parse(getPathWithFile());
    }

    public void setUri(Uri uri) {
        pathWithFile = uri.toString();
    }

    public String getPathWithFile() {
        return pathWithFile;
    }

    public void setPathWithFile(String pathWithFile) {
        this.pathWithFile = IMAGE_ROOT_URL + pathWithFile;
    }
}
