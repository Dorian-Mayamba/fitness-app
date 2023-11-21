package uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.BodyPartViewModel;

public class BodyPartsCallBack implements Callback<List<String>> {

    private BodyPartViewModel model;

    public BodyPartsCallBack(BodyPartViewModel model){
        this.model = model;
    }
    @Override
    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        if(response.isSuccessful()){
            Log.i(MainActivity.TAG, "adding all the body parts to the list");
            model.addAllBodyParts(response.body());
        }else{
            try {
                Log.e(MainActivity.TAG, "Error: " + response.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onFailure(Call<List<String>> call, Throwable t) {
        Log.e(MainActivity.TAG, t.getMessage());
    }
}
