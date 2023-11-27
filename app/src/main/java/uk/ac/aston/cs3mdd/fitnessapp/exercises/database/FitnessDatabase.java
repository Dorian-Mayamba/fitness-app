package uk.ac.aston.cs3mdd.fitnessapp.exercises.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.daos.ExerciseDao;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.daos.WorkoutPlanDao;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.WorkoutPlan;


@Database(entities = {Exercise.class, WorkoutPlan.class}, version = 1)
public abstract class FitnessDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutPlanDao workoutPlanDao();
}
