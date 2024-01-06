package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.models.ExercisesViewModel;

public class ExercisesCallback implements Callback<List<Exercise>> {

    private ExercisesViewModel exercisesViewModel;

    public ExercisesCallback(ExercisesViewModel model){
        this.exercisesViewModel = model;
    }
    @Override
    public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
        if(response.isSuccessful()){
            exercisesViewModel.addExercises(response.body());
        }else{
            try {
                Log.e(MainActivity.TAG, "Error: "+ response.errorBody().string());
            } catch (IOException e) {
                //Ignore
            }
        }
    }

    @Override
    public void onFailure(Call<List<Exercise>> call, Throwable t) {
        Log.i(MainActivity.TAG + "Exercise Callback", "Error: " + t.getMessage());
    }
}
