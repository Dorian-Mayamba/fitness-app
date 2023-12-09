package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> currentLocation;

    private MutableLiveData<Boolean> isUpdateRequired;

    public LocationViewModel(){
        super();
        currentLocation = new MutableLiveData<Location>();
        isUpdateRequired = new MutableLiveData<Boolean>();
    }

    public LiveData<Location> getCurrentLocation(){
        return this.currentLocation;
    }

    public void setCurrentLocation(Location location){
        this.currentLocation.setValue(location);
    }

    public LiveData<Boolean> isLocationUpdateRequired(){
        return this.isUpdateRequired;
    }

    public void setIsUpdateRequired(Boolean value){
        this.isUpdateRequired.setValue(value);
    }

}
