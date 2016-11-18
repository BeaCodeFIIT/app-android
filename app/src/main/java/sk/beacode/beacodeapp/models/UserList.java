package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sandra on 18.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList {
    @JsonProperty("data")
    private User user;

    public User getUser() {
        return user;
    }
}
