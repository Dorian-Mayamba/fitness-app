package uk.ac.aston.cs3mdd.fitnessapp.database;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.ac.aston.cs3mdd.fitnessapp.database.daos.ExerciseDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.daos.WorkoutPlanDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.daos.WorkoutPlanWithExercisesDao;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;


@Database(entities = {Exercise.class, WorkoutPlan.class}, version = 5)
public abstract class FitnessDatabase extends RoomDatabase {

    private static FitnessDatabase instance;
    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutPlanDao workoutPlanDao();

    public static Executor dbQueryExecutor =
            Executors.newFixedThreadPool(4);
    public static Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    public abstract WorkoutPlanWithExercisesDao workoutPlanWithExercisesDao();

    public static FitnessDatabase getInstance(Context context){
        if (instance == null){
            synchronized (FitnessDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context, FitnessDatabase.class,
                    "Fitness_database").build();
                }
            }
        }
        return instance;
    }
}
