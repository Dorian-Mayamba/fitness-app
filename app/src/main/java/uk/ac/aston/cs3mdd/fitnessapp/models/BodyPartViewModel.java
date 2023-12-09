package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.BodyPartsCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;

public class BodyPartViewModel extends ViewModel {
    private MutableLiveData<List<String>> allBodyParts;

    public BodyPartViewModel(){
        super();
        allBodyParts = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<String>> getAllBodyParts() {
        return allBodyParts;
    }

    public void requestBodyParts(ExerciseRepository exerciseRepository){
        if(allBodyParts.getValue().size() > 0){
            Log.i(MainActivity.TAG, "target list not empty, request aborted");
            return;
        }
        Call<List<String>> bodyPartsCall = exerciseRepository.getBodyParts();
        bodyPartsCall.enqueue(new BodyPartsCallBack(this));
    }

    public void addAllBodyParts(List<String> targets){
        this.allBodyParts.getValue().addAll(targets);
        this.allBodyParts.setValue(this.allBodyParts.getValue());
    }
}
