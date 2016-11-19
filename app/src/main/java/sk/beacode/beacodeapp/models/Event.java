package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;
import android.media.*;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.media.CamcorderProfile.get;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements SearchSuggestion {
    private int id;
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy HH:MM")
    private Date start;
    @JsonFormat(pattern = "dd.MM.yyyy HH:MM")
    private Date end;
    private Location location;
    private String description;
    private List<Image> images;
    private List<Exhibit> exhibitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exhibit> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<Exhibit> exhibitions) {
        this.exhibitions = exhibitions;
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

    }
}
