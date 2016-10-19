package sk.beacode.beacodeapp.models;

import java.util.Date;
import java.util.List;

public class Event {
    private String name;
    private Date start;
    private Date end;
    private String location;
    private String description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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
}
