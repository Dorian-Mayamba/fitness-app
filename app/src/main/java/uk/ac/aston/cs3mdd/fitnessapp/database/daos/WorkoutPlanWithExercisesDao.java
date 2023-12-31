package uk.ac.aston.cs3mdd.fitnessapp.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import uk.ac.aston.cs3mdd.fitnessapp.database.relationships.WorkoutPlansWithExercises;

@Dao
public interface WorkoutPlanWithExercisesDao {
    @Transaction
    @Query("SELECT * FROM workoutplan WHERE day=:day")
    WorkoutPlansWithExercises getWorkoutPlanWithExercises(String day);
}
