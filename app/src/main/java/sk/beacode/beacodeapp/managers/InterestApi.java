package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.InterestList;

public interface InterestApi {
    @GET("interests")
    Call<InterestList> getInterests();

    @POST("interests/new")
    Call<Void> addInterest(@Body Interest interest);

    @DELETE("interests/{interestId}")
    Call<Void> deleteInterest(@Path("interestId") int interestId);
}
