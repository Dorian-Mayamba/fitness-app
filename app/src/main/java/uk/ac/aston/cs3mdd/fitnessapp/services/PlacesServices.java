package uk.ac.aston.cs3mdd.fitnessapp.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetailResponse;

public interface PlacesServices {
    @GET("place/nearbysearch/json?")
    Call<PlaceList> getAllPlace(@Query("location") String loc, @Query("radius")
                                       int radius, @Query("type") String type, @Query("key") String key);

    @GET("place/details/json?")
    Call<PlaceDetailResponse> getPlaceDetail(@Query("fields") String fields,
                                             @Query("place_id") String placeId, @Query("key") String key);

    @GET("place/photo?")
    Call<ResponseBody> getPhotos(@Query("photo_reference") String photoId, @Query("maxwidth") int maxWidth, @Query("maxheight") int maxHeight, @Query("key")String apiKey);
}
