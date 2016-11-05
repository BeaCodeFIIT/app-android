package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExhibitList {
    @JsonProperty("data")
    private List<Exhibit> exhibits;

    public List<Exhibit> getExhibits() {
        return exhibits;
    }
}
