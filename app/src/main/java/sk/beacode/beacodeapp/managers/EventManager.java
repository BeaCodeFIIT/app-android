package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import sk.beacode.beacodeapp.models.EventList;
import sk.beacode.beacodeapp.models.EventQuery;

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface EventManager {

    @Post("/events")
    EventList getEvents();

    @Post("/events")
    EventList getEventsByNamePart(@Body EventQuery query);
}
