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
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.ExercisesCallback;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;

public class ExercisesViewModel extends ViewModel {
    private MutableLiveData<List<Exercise>> allExercises;

    public ExercisesViewModel() {
        super();
        allExercises = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void requestExercises(ExerciseRepository exerciseRepository) {
        if (this.allExercises.getValue().size() > 0) {
            Log.i(MainActivity.TAG, "Exercises list not empty, cannot display other exercises");
        } else {
            Call<List<Exercise>> exercisesCall = exerciseRepository.getExercises();
            exercisesCall.enqueue(new ExercisesCallback(this));
        }
    }

    public void requestExercisesFromBodyPart(ExerciseRepository repository, String bodyPart) {
        if (this.allExercises.getValue().size() > 0) {
            Log.i(MainActivity.TAG, "Exercises list not empty, clearing the list");
            this.allExercises.getValue().clear();
        }
        Call<List<Exercise>> exercisesCall = repository.getExercisesFromBodyPart(bodyPart);
        exercisesCall.enqueue(new ExercisesCallback(this));
    }

    public void addExercises(List<Exercise> exercises) {
        Log.i(MainActivity.TAG, "Adding " + exercises.size() + " exercises to the list");
        this.allExercises.getValue().addAll(exercises);
        this.allExercises.setValue(this.allExercises.getValue());
    }
}
