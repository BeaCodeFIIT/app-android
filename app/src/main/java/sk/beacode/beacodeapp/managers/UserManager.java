package sk.beacode.beacodeapp.managers;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.User;
import sk.beacode.beacodeapp.models.UserList;

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface UserManager {

    @Get("/logged-in-user")
    UserList getLoggedInUser();

}
