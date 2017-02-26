package sk.beacode.beacodeapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandra on 25.02.2017.
 */

public class Category {

    private Integer id;
    private String name;
    private List<Exhibit> exhibits = new ArrayList<>();

    public void setExhibits(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
