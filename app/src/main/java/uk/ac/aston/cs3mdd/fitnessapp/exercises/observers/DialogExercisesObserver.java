package uk.ac.aston.cs3mdd.fitnessapp.exercises.observers;

import android.util.Log;

import androidx.lifecycle.Observer;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.EditExerciseAdapter;

public class DialogExercisesObserver implements Observer<List<Exercise>> {

    private EditExerciseAdapter adapter;
    public DialogExercisesObserver(EditExerciseAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public void onChanged(List<Exercise> exercises) {
        Log.i(MainActivity.TAG, "displaying items");
        for (Exercise e:exercises){
            Log.i(MainActivity.TAG, e.toString());
        }
        adapter.submitList(exercises);
    }
}
