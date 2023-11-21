package uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.ExercisesViewModel;

public class ExercisesCallback implements Callback<List<Exercise>> {

    private ExercisesViewModel exercisesViewModel;

    public ExercisesCallback(ExercisesViewModel model){
        this.exercisesViewModel = model;
    }
    @Override
    public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
        if(response.isSuccessful()){
            exercisesViewModel.addExercises(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Exercise>> call, Throwable t) {
        Log.i(MainActivity.TAG, "Error: " + t.getMessage());
    }
}
