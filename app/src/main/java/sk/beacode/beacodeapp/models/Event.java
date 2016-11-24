package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements SearchSuggestion {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private String description;
    private Location location;
    private List<Image> images;
    private List<Exhibit> exhibits;

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[0];
        }
    };

    public Event() {}

    private Event(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        start = new Date(parcel.readLong());
        end = new Date(parcel.readLong());
        description = parcel.readString();
        location = (Location) parcel.readSerializable();
        parcel.readList(images, null);
        parcel.readList(exhibits, null);
    }

    @Override
    public String getBody() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeLong(start.getTime());
        parcel.writeLong(end.getTime());
        parcel.writeString(description);
        parcel.writeSerializable(location);
        parcel.writeList(images);
        parcel.writeList(exhibits);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Bitmap getMainImage() {
        if (images == null) {
            return null;
        }
        return images.get(0).getBitmap();
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }
}
