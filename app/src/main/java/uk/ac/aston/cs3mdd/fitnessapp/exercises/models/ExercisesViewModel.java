package uk.ac.aston.cs3mdd.fitnessapp.exercises.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks.ExercisesCallback;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories.ExerciseRepository;

public class ExercisesViewModel extends ViewModel {
    private MutableLiveData<List<Exercise>> allExercises;

    public ExercisesViewModel(){
        super();
        allExercises = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void requestExercises(ExerciseRepository exerciseRepository){
        if(this.allExercises.getValue().size() > 0){
            Log.i(MainActivity.TAG, "Exercises list not empty, cannot display other exercises");
        }else{
            Call<List<Exercise>> exercisesCall = exerciseRepository.getExercises();
            exercisesCall.enqueue(new ExercisesCallback(this));
        }
    }

    public void addExercises(List<Exercise> exercises){
        Log.i(MainActivity.TAG, "Adding "+ exercises.size() + " exercises to the list");
        this.allExercises.getValue().addAll(exercises);
        this.allExercises.setValue(this.allExercises.getValue());
    }
}
