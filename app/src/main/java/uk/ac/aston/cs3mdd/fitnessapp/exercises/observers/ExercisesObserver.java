package uk.ac.aston.cs3mdd.fitnessapp.exercises.observers;

import android.util.Log;

import androidx.lifecycle.Observer;

import java.util.List;
import java.util.Locale;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

public class ExercisesObserver implements Observer<List<Exercise>> {
    @Override
    public void onChanged(List<Exercise> exercises) {
        if(exercises.size() > 0){
            Log.i(MainActivity.TAG, "Displaying "+ exercises.size() + " exercises");
            for(Exercise exercise:exercises){
                if(exercise.getLanguage() == 2){
                    Log.i(MainActivity.TAG, exercise.toString());
                }
            }
        }
    }
}
