package uk.ac.aston.cs3mdd.fitnessapp.listeners;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;

public interface OnEditConfirmListener {
    void OnEditExercise(Exercise exercise, int numberOfSets, int numberOfReps, String day);
}
