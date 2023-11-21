package uk.ac.aston.cs3mdd.fitnessapp.exercises.observers;

import android.util.Log;
import androidx.lifecycle.Observer;
import java.util.List;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.BodyPartsAdapter;

public class TargetObserver implements Observer<List<String>> {
    private BodyPartsAdapter adapter;

    public TargetObserver(BodyPartsAdapter adapter){
        this.adapter = adapter;
    }
    @Override
    public void onChanged(List<String> bodyParts) {
        Log.i(MainActivity.TAG, "Displaying "+ bodyParts.size() + " targets");
        for(String bodyPart:bodyParts){
            Log.i(MainActivity.TAG, bodyPart);
        }
        adapter.updateItems(bodyParts);
    }
}
