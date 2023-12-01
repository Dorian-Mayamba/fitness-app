package uk.ac.aston.cs3mdd.fitnessapp.exercises.callbacks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

public class ExerciseAdapterCallback extends DiffUtil.ItemCallback<Exercise> {

    @Override
    public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
        return oldItem.equals(newItem);
    }
}
