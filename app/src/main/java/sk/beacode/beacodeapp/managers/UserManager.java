package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;
import sk.beacode.beacodeapp.models.InterestList;

@Rest(rootUrl = Manager.ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface UserManager {

//    @Get("/interests/show")
//    InterestList getInterests();


//    @Get("/users?name={name}")
//    User getUserByName(@Path String name);
//
//    @Post("{userName}")
//    void addInterestByUserName(@Body Interest interest, @Path String userName);
//
//    @Delete("{userName}")
//    void deleteInterestByUserName(@Body Interest interest, @Path String userName);
//
//    @Post("{userName}")
//    void setPhotoByUserName(@Body Byte[] photo, @Path String userName);
}
