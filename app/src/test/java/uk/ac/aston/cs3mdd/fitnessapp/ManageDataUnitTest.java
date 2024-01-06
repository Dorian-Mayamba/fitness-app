package uk.ac.aston.cs3mdd.fitnessapp;

import android.content.Context;
import android.os.Handler;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.Random;

import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.relationships.WorkoutPlansWithExercises;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;

@RunWith(RobolectricTestRunner.class)
public class ManageDataUnitTest {


    private final String [] days = DayUtil.getDays();


    @Mock
    private Handler handler = FitnessDatabase.handler;

    @Mock
    Context context;

    @Test
    public void itShouldLoadWorkoutPlan(){
        context = ApplicationProvider.getApplicationContext();
        FitnessDatabase database = FitnessDatabase.getInstance(context);
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(days.length);
        String randomDay = days[randomIndex];
        FitnessDatabase.dbQueryExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WorkoutPlansWithExercises plan = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises(randomDay);
                handler.post(new Runnable() {
                    private WorkoutPlansWithExercises workoutPlan;
                    @Override
                    public void run() {
                        workoutPlan = plan;
                        Assert.assertNotNull(workoutPlan);
                    }
                });
            }
        });
    }

    @Test
    public void itShouldUpdateWorkoutPlan(){
        context = ApplicationProvider.getApplicationContext();
        FitnessDatabase database = FitnessDatabase.getInstance(context);
        FitnessDatabase.dbQueryExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WorkoutPlansWithExercises plansWithExercises = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises("Tuesday");
                List<Exercise> exerciseList = plansWithExercises.getExercises();
                Exercise exercise = exerciseList.get(0);
                if (exercise != null){
                    exercise.setWorkoutDay("Tuesday");
                    database.exerciseDao().updateExercise(exercise);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Assert.assertEquals("Tuesday", exercise.getWorkoutDay());
                    }
                });
            }
        });
    }

}
