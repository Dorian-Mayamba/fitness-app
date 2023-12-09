package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.collections.PlaceList;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;

public class PlaceListCallBack implements Callback<PlaceList> {

    private PlaceViewModel placeViewModel;

    public PlaceListCallBack(PlaceViewModel model){
        this.placeViewModel = model;
    }

    @Override
    public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
        if(response.isSuccessful()){
            if (!response.body().getStatus().equals("OK")){
                Log.i(MainActivity.TAG, "Error: "+ response.body().getStatus());
            }else {
                placeViewModel.addAllPlaces(response.body());
            }
        }else {
            Log.i(MainActivity.TAG, "error: "+ response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<PlaceList> call, Throwable t) {
        Log.i(MainActivity.TAG, "error: "+ t.getMessage());
    }
}
