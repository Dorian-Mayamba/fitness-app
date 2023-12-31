package uk.ac.aston.cs3mdd.fitnessapp.listeners;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;

public interface OnAddExerciseConfirmListener {
    void onAddConfirm(Exercise exercise, int numberOfSets, int numberOfReps, String day);
}
