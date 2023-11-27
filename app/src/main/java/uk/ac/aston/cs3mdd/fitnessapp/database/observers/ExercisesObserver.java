package uk.ac.aston.cs3mdd.fitnessapp.database.observers;

import android.util.Log;

import androidx.lifecycle.Observer;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.database.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;

public class ExercisesObserver implements Observer<List<Exercise>> {

    private ExerciseAdapter exerciseAdapter;

    public ExercisesObserver(ExerciseAdapter adapter) {
        this.exerciseAdapter = adapter;
    }

    @Override
    public void onChanged(List<Exercise> exercises) {
        Log.i(MainActivity.TAG, "Updating the adapter list");
        this.exerciseAdapter.updateExercisesList(exercises);
    }
}
