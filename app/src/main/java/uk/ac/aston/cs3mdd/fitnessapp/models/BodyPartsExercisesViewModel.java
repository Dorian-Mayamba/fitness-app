package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.BodyPartsExercisesCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;

public class BodyPartsExercisesViewModel extends ViewModel {
    private MutableLiveData<List<Exercise>> allExercises;

    public BodyPartsExercisesViewModel() {
        super();
        allExercises = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void requestExercisesFromBodyPart(ExerciseRepository repository, String bodyPart) {
        if (this.allExercises.getValue().size() > 0){
            Log.i(MainActivity.TAG, "Clearing the old list");
            allExercises.getValue().clear();
        }
        Call<List<Exercise>> exercisesCall = repository.getExercisesFromBodyPart(bodyPart);
        exercisesCall.enqueue(new BodyPartsExercisesCallBack(this));
    }

    public void addExercises(List<Exercise> exercises) {
        Log.i(MainActivity.TAG, "Adding the exercises to the list");
        allExercises.getValue().addAll(exercises);
        allExercises.setValue(allExercises.getValue());
    }
}
