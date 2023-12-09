package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.models.BodyPartsExercisesViewModel;

public class BodyPartsExercisesCallBack implements Callback<List<Exercise>> {

    private BodyPartsExercisesViewModel model;

    public BodyPartsExercisesCallBack(BodyPartsExercisesViewModel model){
        this.model = model;
    }

    @Override
    public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
        if(response.isSuccessful()){
            model.addExercises(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Exercise>> call, Throwable t) {
        Log.i(MainActivity.TAG, "Error: "+ t.getMessage());
    }
}
