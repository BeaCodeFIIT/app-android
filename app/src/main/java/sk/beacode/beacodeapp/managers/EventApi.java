package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.EventList;

public interface EventApi {
    @POST("events")
    Call<EventList> getEvents();

    @POST("events")
    Call<Event> getEventsByNamePart(@Body String namePart);
}
