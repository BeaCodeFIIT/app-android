package sk.beacode.beacodeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exhibit implements Parcelable {
    private int id;
    private String name;
    private String description;
    private Date start;
    private Date end;
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

    public List<Image> getImages() {
        return images;
    }

    public Image getMainImage() {
        if (images == null) {
            return null;
        }
        return images.get(0);
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }


    public Date getStart() {
        return start;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setEnd(Date end) {
        this.end = end;
    }
}
