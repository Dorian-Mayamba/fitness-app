package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.observers;

import androidx.lifecycle.Observer;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;

public class ExercisesObserver implements Observer<List<Exercise>> {

    private ExerciseAdapter exerciseAdapter;

    public ExercisesObserver(ExerciseAdapter adapter){
        this.exerciseAdapter = adapter;
    }

    @Override
    public void onChanged(List<Exercise> exercises) {
        if(exercises.size() > 0){
            this.exerciseAdapter.updateExercisesList(exercises);
        }
    }
}
