package uk.ac.aston.cs3mdd.fitnessapp.exercises.observers;

import android.util.Log;

import androidx.lifecycle.Observer;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

public class ExerciseFromBodyPartObserver implements Observer<List<Exercise>> {

    private List<Exercise> exercises;
    public ExerciseFromBodyPartObserver(List<Exercise> exerciseList){
        this.exercises = exerciseList;
    }
    @Override
    public void onChanged(List<Exercise> exerciseList) {
        if(this.exercises.size() > 0){
            Log.i(MainActivity.TAG, "Clearing the old exercises size to update the workout view");
            this.exercises.clear();
        }
        this.exercises.addAll(exerciseList);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
