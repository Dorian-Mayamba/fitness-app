package uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.collections.ExerciseList;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.ExercisesViewModel;

public class ExercisesCallback implements Callback<ExerciseList> {

    private ExercisesViewModel exercisesViewModel;

    public ExercisesCallback(ExercisesViewModel model){
        this.exercisesViewModel = model;
    }
    @Override
    public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {
        if(response.isSuccessful()){
            exercisesViewModel.addExercises(response.body());
        }
    }

    @Override
    public void onFailure(Call<ExerciseList> call, Throwable t) {
        Log.i(MainActivity.TAG, "Error: " + t.getMessage());
    }
}
