package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Interest {
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public Interest setName(String name) {
        this.name = name;
        return this;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
