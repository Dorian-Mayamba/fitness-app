package uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class BodyPartsAdapter extends ArrayAdapter<String> {

    List<String> bodyParts;
    public BodyPartsAdapter(@NonNull Context context, int resource, List<String> bodyParts) {
        super(context, resource, bodyParts);
    }

    public void updateItems(List<String> bodyParts){
        this.bodyParts = bodyParts;
        notifyDataSetChanged();
    }


}
