package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import sk.beacode.beacodeapp.models.ExhibitList;

/**
 * Created by Sandra on 26.02.2017.
 */

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface CategoryManager {

    @Get("/events/{eventId}/exhibits")
    ExhibitList getExhibitsByCategoryId(@Path int eventId);
}
