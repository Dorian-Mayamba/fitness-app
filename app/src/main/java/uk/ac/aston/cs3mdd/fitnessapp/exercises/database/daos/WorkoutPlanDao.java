package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.WorkoutPlan;

@Dao
public interface WorkoutPlanDao {
    @Query("SELECT * FROM workoutplan")
    List<WorkoutPlan> getAllWorkoutPlans();

    @Query("SELECT * FROM workoutplan WHERE id=:workoutId")
    WorkoutPlan getWorkoutPlanById(int workoutId);

    @Query("SELECT * FROM workoutplan WHERE day=:day")
    WorkoutPlan getWorkoutPlanByDay(String day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createWorkoutPlan(WorkoutPlan plan);

    @Delete
    void deleteWorkoutPlan(WorkoutPlan plan);

}
