package uk.ac.aston.cs3mdd.fitnessapp.exercises.observers;

import android.util.Log;

import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.ExerciseAdapter;

public class ExercisesObserver implements Observer<List<Exercise>> {

    private ExerciseAdapter exerciseAdapter;
    public ExercisesObserver(ExerciseAdapter exerciseAdapter){
        this.exerciseAdapter = exerciseAdapter;
    }
    @Override
    public void onChanged(List<Exercise> exercises) {
        exerciseAdapter.updateList(exercises);
    }
}
