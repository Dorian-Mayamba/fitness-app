package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.PlaceListCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;

public class PlaceViewModel extends ViewModel {
    private MutableLiveData<List<Place>> allPlaces;

    public PlaceViewModel(){
        super();
        allPlaces = new MutableLiveData<>(new ArrayList<>());
    }

    public void requestPlaces(PlacesRepository repository, String keyWord, String location, int radius, String type, String key){
        Call<PlaceList> placeListCall = repository.getAllPlaces(keyWord, location,radius,type,key);
        placeListCall.enqueue(new PlaceListCallBack(this));
    }

    public LiveData<List<Place>> getAllPlaces(){
        return this.allPlaces;
    }

    public void addAllPlaces(PlaceList places){
        if (places.getResults().size() > 0){
            Log.i(MainActivity.TAG, "Adding places to the list");
            allPlaces.getValue().addAll(places.getResults());
            allPlaces.setValue(allPlaces.getValue());
        }
    }
}
