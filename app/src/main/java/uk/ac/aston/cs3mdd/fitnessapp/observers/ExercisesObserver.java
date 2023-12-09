package uk.ac.aston.cs3mdd.fitnessapp.observers;

import androidx.lifecycle.Observer;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.ExerciseAdapter;

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
