package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;

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
