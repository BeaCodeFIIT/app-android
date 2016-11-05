package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@Rest(rootUrl = Manager.ROOT_URL, converters = {StringHttpMessageConverter.class})
public interface UserManager {
    @Get("/users?name={name}")
    User getUserByName(@Path String name);

    @Post("{userName}")
    void addInterestByUserName(@Body Interest interest, @Path String userName);

    @Delete("{userName}")
    void deleteInterestByUserName(@Body Interest interest, @Path String userName);

    @Post("{userName}")
    void setPhotoByUserName(@Body Byte[] photo, @Path String userName);
}
