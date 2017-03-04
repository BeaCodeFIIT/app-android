package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sk.beacode.beacodeapp.models.ExhibitList;

public interface ExhibitApi {
    @GET("events/{eventId}/exhibits")
    Call<ExhibitList> getExhibitsByEventId(@Path("eventId") int eventId);
}
