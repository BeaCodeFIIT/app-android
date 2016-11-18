package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestList {
    @JsonProperty("data")
    private List<Interest> interests;

    public List<Interest> getInterests() {
        return interests;
    }
}
