package uk.ac.aston.cs3mdd.fitnessapp.generics;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;

public abstract class AbstractViewModel<T> extends ViewModel implements IViewModel<T>{
    private MutableLiveData<List<T>> allElements;

    public AbstractViewModel(){
        super();
        allElements = new MutableLiveData<List<T>>(new ArrayList<T>());
    }

    public LiveData<List<T>> getAllElements() {
        return allElements;
    }

    @Override
    public void updateListFromResultQuery(List<T> elements) {
        if(allElements.getValue().size() > 0){
            Log.i(MainActivity.TAG, "Clearing the list");
            allElements.getValue().clear();
        }
        allElements.getValue().addAll(elements);
        allElements.setValue(allElements.getValue());
    }
}
