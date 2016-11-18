package sk.beacode.beacodeapp.models;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exhibit {
    private int id;
    private String name;
    private String description;
    private List<Image> images;

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
}
