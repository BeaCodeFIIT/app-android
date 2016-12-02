package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import sk.beacode.beacodeapp.models.User;

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class, ByteArrayHttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface UserManager {

    @Get("/logged-in-user")
    User getLoggedInUser();

    @Post("/logged-in-user/images/new")
    @Headers({
            @Header(name = "content-type", value = "multipart/form-data"),
            @Header(name = "Content-Disposition", value = "form-data"),
            @Header(name = "name", value = "image"),
            @Header(name = "filename", value="ic_profile-active.png"),
            @Header(name = "Content-Type", value = "image/png")
    })
    void updateImage(@Body byte[] data);
}
