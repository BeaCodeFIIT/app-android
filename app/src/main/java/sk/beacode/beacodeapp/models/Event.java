package sk.beacode.beacodeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements SearchSuggestion, Parcelable {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private String description;
    private Location location;
    private List<Image> images;
    private List<Exhibit> exhibits;
    private List<Category> categories;
    private List<Beacon> beacons;
    private Image map;

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[i];
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
        parcel.readList(images, Image.class.getClassLoader());
        parcel.readList(exhibits, Exhibit.class.getClassLoader());
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

    public List<Image> getImages() {
        System.out.println(images);
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Image getMainImage() {
        return images != null ? images.get(0) : null;
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Image getMap() {
        return map;
    }

    public void setMap(Image map) {
        this.map = map;
    }

    public Beacon getBeacon(int minor) {
        for (Beacon beacon : beacons) {
            if (beacon.getMinor() == minor) {
                return beacon;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (getId() != event.getId()) return false;
        if (getName() != null ? !getName().equals(event.getName()) : event.getName() != null)
            return false;
        if (getStart() != null ? !getStart().equals(event.getStart()) : event.getStart() != null)
            return false;
        if (getEnd() != null ? !getEnd().equals(event.getEnd()) : event.getEnd() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(event.getDescription()) : event.getDescription() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(event.getLocation()) : event.getLocation() != null)
            return false;
        if (getImages() != null ? !getImages().equals(event.getImages()) : event.getImages() != null)
            return false;
        if (getExhibits() != null ? !getExhibits().equals(event.getExhibits()) : event.getExhibits() != null)
            return false;
        if (getCategories() != null ? !getCategories().equals(event.getCategories()) : event.getCategories() != null)
            return false;
        if (getBeacons() != null ? !getBeacons().equals(event.getBeacons()) : event.getBeacons() != null)
            return false;
        return getMap() != null ? getMap().equals(event.getMap()) : event.getMap() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getImages() != null ? getImages().hashCode() : 0);
        result = 31 * result + (getExhibits() != null ? getExhibits().hashCode() : 0);
        result = 31 * result + (getCategories() != null ? getCategories().hashCode() : 0);
        result = 31 * result + (getBeacons() != null ? getBeacons().hashCode() : 0);
        result = 31 * result + (getMap() != null ? getMap().hashCode() : 0);
        return result;
    }
}
