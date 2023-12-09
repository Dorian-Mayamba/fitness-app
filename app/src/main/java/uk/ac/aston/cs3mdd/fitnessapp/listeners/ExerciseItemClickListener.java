package uk.ac.aston.cs3mdd.fitnessapp.listeners;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;

public interface ExerciseItemClickListener {
    default void onEditExerciseClick(Exercise editedExercise){
        throw new IllegalStateException("No implementation found");
    }

    default void onAddExerciseClick(Exercise newExercise){
        throw new IllegalStateException("No implementation found");
    }
}
