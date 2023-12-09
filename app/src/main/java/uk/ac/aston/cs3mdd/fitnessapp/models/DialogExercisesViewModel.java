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
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.DialogExercisesCallback;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;

public class DialogExercisesViewModel extends ViewModel {
    private MutableLiveData<List<Exercise>> allExercises;
    public DialogExercisesViewModel(){
        super();
        allExercises = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Exercise>> getAllExercises(){
        return this.allExercises;
    }

    public void requestExercise(ExerciseRepository repository, String bodyPart){
        Call<List<Exercise>> listCall = repository.getExercisesFromBodyPart(bodyPart);
        listCall.enqueue(new DialogExercisesCallback(this));
    }

    public void addExercise(List<Exercise> exercises){
        if(allExercises.getValue().size() > 0){
            Log.i(MainActivity.TAG, "Clearing the list");
        }
        allExercises.getValue().addAll(exercises);
        allExercises.setValue(allExercises.getValue());
    }
}
