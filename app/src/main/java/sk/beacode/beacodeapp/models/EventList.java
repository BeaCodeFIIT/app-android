package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventList {
    @JsonProperty("data")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }
}
