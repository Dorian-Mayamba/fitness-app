package uk.ac.aston.cs3mdd.fitnessapp.database.relationships;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;

public class WorkoutPlansWithExercises {
    @Embedded
    private WorkoutPlan workoutPlan;
    @Relation(
            parentColumn = "day",
            entityColumn = "workout_day"
    )
    private List<Exercise> exercises;

    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }

    public void setWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlan = workoutPlan;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
