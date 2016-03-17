package hu.core.retrofit;


import java.util.List;

import hu.core.api.Hotel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by JGabrielFreitas on 16/03/16.
 */
public interface ServiceApi {

    @GET("/hotels")
    Call<List<Hotel>> getHotelsList();

    @GET("/hotel/{id}")
    Call<Hotel> getHotel(@Path("id") int id);
}
