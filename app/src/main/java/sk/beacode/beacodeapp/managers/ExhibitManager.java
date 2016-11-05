package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import sk.beacode.beacodeapp.models.ExhibitList;

@Rest(rootUrl = Manager.ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface ExhibitManager {

    @Get("/exhibits/show/{eventId}")
    ExhibitList getByEventId(@Path int eventId);
}

