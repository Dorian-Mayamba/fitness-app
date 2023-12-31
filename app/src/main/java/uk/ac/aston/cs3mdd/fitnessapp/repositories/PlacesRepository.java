package uk.ac.aston.cs3mdd.fitnessapp.repositories;

import okhttp3.ResponseBody;
import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetailResponse;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;

public class PlacesRepository {
    private PlacesServices placesServices;

    public PlacesRepository(PlacesServices services){
        this.placesServices = services;
    }

    public Call<PlaceList> getAllPlaces(String location, int radius, String type, String key){
        return placesServices.getAllPlace(location, radius, type, key);
    }

    public Call<PlaceDetailResponse> getPlaceDetail(String fields, String placeId, String apiKey){
        return this.placesServices.getPlaceDetail(fields,placeId,apiKey);
    }
    public Call<ResponseBody> getPlacePhoto(String photoReference, int maxWidth, int maxHeight, String apiKey){
        return this.placesServices.getPhotos(photoReference,maxWidth,maxHeight, apiKey);
    }
}
