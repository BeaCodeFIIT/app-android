package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sk.beacode.beacodeapp.models.AddSelectedExhibit;
import sk.beacode.beacodeapp.models.SelectedExhibitList;

public interface SelectedExhibitsApi {
	@GET("events/{eventId}/selected-exhibits-for-tour")
	Call<SelectedExhibitList> getSelectedExhibits(@Path("eventId") int eventId);

	@POST("events/{eventId}/selected-exhibits-for-tour/new")
	Call<Void> addSelectedExhibit(@Path("eventId") int eventId, @Body AddSelectedExhibit addSelectedExhibit);

	@DELETE("events/{eventId}/selected-exhibits-for-tour/{exhibitId}")
	Call<Void> deleteSelectedExhibit(@Path("eventId") int eventId, @Path("exhibitId") int exhibitId);
}
