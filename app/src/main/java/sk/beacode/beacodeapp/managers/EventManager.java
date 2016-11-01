package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.List;

import sk.beacode.beacodeapp.models.Event;

@Rest(rootUrl = "http://", converters = {StringHttpMessageConverter.class})
public interface EventManager {
    @Get("/events")
    List<Event> getEvents();

    @Get("/events?search={query}")
    List<Event> searchEventsByName(@Path String query);
}
