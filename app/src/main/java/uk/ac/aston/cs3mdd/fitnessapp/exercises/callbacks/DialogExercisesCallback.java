package uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks;

import android.util.Log;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.DialogExercisesViewModel;

public class DialogExercisesCallback implements Callback<List<Exercise>> {


    private DialogExercisesViewModel model;

    public DialogExercisesCallback(DialogExercisesViewModel model){
        this.model = model;
    }


    @Override
    public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
        if (response.isSuccessful()){
            model.addExercise(response.body());
        }else{
            Log.i(MainActivity.TAG, "Error: "+ response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Exercise>> call, Throwable t) {
        Log.i(MainActivity.TAG, "Error: "+ t.getMessage());
    }
}
