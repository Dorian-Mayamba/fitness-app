package uk.ac.aston.cs3mdd.fitnessapp.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;

public interface PlacesServices {
    @GET("json?")
    Call<PlaceList> getAllPlace(@Query("keyword") String keyWord, @Query("location") String loc, @Query("radius")
                                       int radius, @Query("type") String type, @Query("key") String key);
}
