package sk.beacode.beacodeapp.managers;

import retrofit2.Call;
import retrofit2.http.POST;

public interface FeedbackApi {

	@POST("feedback/positive")
	Call<Void> sendPositiveFeedback();

	@POST("feedback/negative")
	Call<Void> sendNegativeFeedback();
}
