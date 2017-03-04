package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sk.beacode.beacodeapp.models.User;

public interface UserApi {

    @GET("logged-in-user")
    Call<User> getLoggedInUser();

    @POST("logged-in-user/images/new")
    Call<Void> updateImage(@Body byte[] data);

}
