package uk.ac.aston.cs3mdd.fitnessapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import uk.ac.aston.cs3mdd.fitnessapp.database.daos.ExerciseDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.daos.WorkoutPlanDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;


@Database(entities = {Exercise.class, WorkoutPlan.class}, version = 2)
public abstract class FitnessDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutPlanDao workoutPlanDao();
}
