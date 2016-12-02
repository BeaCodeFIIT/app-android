package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exhibit implements Parcelable {
    private int id;
    private String name;
    private String description;
    private List<Image> images;
    private List<Beacon> beacons;

    public static final Creator<Exhibit> CREATOR = new Creator<Exhibit>() {
        @Override
        public Exhibit createFromParcel(Parcel parcel) {
            return new Exhibit(parcel);
        }

        @Override
        public Exhibit[] newArray(int i) {
            return new Exhibit[i];
        }
    };

    public Exhibit() {}

    private Exhibit(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        description = parcel.readString();
        parcel.readList(images, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeList(images);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Bitmap> getImages() {
        List<Bitmap> bitmaps = new ArrayList<>();
        if (images != null) {
            for (int i = 1; i < images.size(); ++i) {
                bitmaps.add(images.get(i).getBitmap());
            }
        }
        return bitmaps;
    }

    public Bitmap getMainImage() {
        if (images == null) {
            return null;
        }
        return images.get(0).getBitmap();
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }
}
