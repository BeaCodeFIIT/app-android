package sk.beacode.beacodeapp.managers;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Manager {

    public static final String API_ROOT_URL = "http://147.175.149.218/beacode_dev/current/web/app.php/api/app/";

    //Singleton
    private static Manager instance = null;

    private static String deviceId;

    public static void initialize(String deviceId) {
        Manager.deviceId = deviceId;
    }

    private Manager() {
        // ku kazdemu requestu ktory posielame sa prida deviceId
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("deviceId", deviceId).build();
                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_ROOT_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Manager getInstance() {
        if(instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public Retrofit retrofit;

    public EventApi getEventApi() {
        return retrofit.create(EventApi.class);
    }

    public ExhibitApi getExhibitApi() {
        return retrofit.create(ExhibitApi.class);
    }

    public InterestApi getInterestApi() {
        return retrofit.create(InterestApi.class);
    }

    public UserApi getUserApi () {
        return retrofit.create(UserApi.class);
    }
}
