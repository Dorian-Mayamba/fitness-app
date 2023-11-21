package uk.ac.aston.cs3mdd.fitnessapp.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.database.daos.ExerciseDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.daos.WorkoutPlanDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;


@Database(entities = {Exercise.class, WorkoutPlan.class}, version = 1)
public abstract class FitnessDatabase extends RoomDatabase {
    public static FitnessDatabase instance;

    public static FitnessDatabase getInstance() {
        if (instance == null){
            Log.i(MainActivity.TAG, "initializing a new Database instance");
            instance = Room.databaseBuilder(getApplicationContext(),
                    FitnessDatabase.class, "fitness_db").build();
        }
        return instance;
    }

    private static Context getApplicationContext(){
        return FitnessApplication.getAppContext();
    }

    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutPlanDao workoutPlanDao();
}
