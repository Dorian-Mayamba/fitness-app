package uk.ac.aston.cs3mdd.fitnessapp.repositories;

import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;

public class PlacesRepository {
    private PlacesServices placesServices;

    public PlacesRepository(PlacesServices services){
        this.placesServices = services;
    }

    public Call<PlaceList> getAllPlaces(String keyWord, String location, int radius, String type, String key){
        return placesServices.getAllPlace(keyWord, location, radius, type, key);
    }
}
