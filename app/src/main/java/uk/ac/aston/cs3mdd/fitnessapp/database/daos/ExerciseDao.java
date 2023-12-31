package uk.ac.aston.cs3mdd.fitnessapp.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    List<Exercise> getExercises();

    @Query("SELECT * FROM exercise WHERE id=:exercise_id")
    Exercise findById(int exercise_id);
    @Insert
    long createExercise(Exercise exercise);

    @Update
    void updateExercise(Exercise exercise);

    @Delete
    void deleteExercise(Exercise exercise);

    @Insert
    List<Long> createMultipleExercises(Exercise... exercises);

    @Delete
    void deleteMultiple(Exercise... exercises);

}
