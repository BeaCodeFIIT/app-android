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

import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.InterestList;

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface InterestManager {

    @Get("/interests")
    InterestList getInterests();

    @Post("/interests/new")
    void addInterest(@Body Interest interest);

    @Delete("/interests/{interestId}")
    void deleteInterest(@Path int interestId);


}
