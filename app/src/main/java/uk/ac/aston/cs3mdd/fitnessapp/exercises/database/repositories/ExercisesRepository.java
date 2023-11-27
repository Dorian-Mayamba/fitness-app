package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.repositories;

import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.callbacks.IExercisesQueryCallback;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.WorkoutPlan;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.results.ExerciseQueryResult;
import uk.ac.aston.cs3mdd.fitnessapp.generics.AbstractRepository;
import uk.ac.aston.cs3mdd.fitnessapp.generics.IViewModel;

public class ExercisesRepository extends AbstractRepository<Exercise> {
    public ExercisesRepository(Executor executor, Handler handler) {
        super(executor, handler);
    }

    private void requestLoadExercises(FitnessDatabase database, IViewModel<Exercise> model, IExercisesQueryCallback callback){
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                Log.i(MainActivity.TAG, "Load exercise request initialised");
                try{
                    List<Exercise> exercises = database.exerciseDao().getExercises();
                    callback.onSuccess(new ExerciseQueryResult.Success<List<Exercise>>(exercises));
                }catch(Exception exception){
                    callback.onError(new ExerciseQueryResult.Error<String>(exception));
                }
            }
        });
    }

    public void loadExercises(FitnessDatabase database, IViewModel<Exercise> model){
        requestLoadExercises(database, model, new IExercisesQueryCallback(){

            @Override
            public void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success) {
                Log.i(MainActivity.TAG, "Notifying the data change to the observer");
                notifyDataSetChange(model,success.resultBody());
            }

            @Override
            public void onError(ExerciseQueryResult.Error<String> error) {
                Log.e(MainActivity.TAG, "Error: "+ error.getErrorMessage());
            }
        });
    }

    private void requestInsertExercise(FitnessDatabase database, IViewModel<Exercise> model, Exercise exercise, String workoutDay, IExercisesQueryCallback callback){
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background task
                try{
                    WorkoutPlan plan = database.workoutPlanDao().getWorkoutPlanByDay(workoutDay);
                    if(plan != null){
                        //Check if there is workout plan
                        exercise.setWorkoutPlanId(plan.getId());
                    }else{
                        WorkoutPlan workoutPlanToCreate = new WorkoutPlan();
                        workoutPlanToCreate.setDay(workoutDay);
                        int planId = (int)database.workoutPlanDao().createWorkoutPlan(workoutPlanToCreate);
                        exercise.setWorkoutPlanId(planId);
                    }
                    database.exerciseDao().createExercise(exercise);
                    List<Exercise> exercises = database.exerciseDao().getExercises();
                    callback.onSuccess(new ExerciseQueryResult.Success<List<Exercise>>(exercises));
                }catch(Exception exception){
                    callback.onError(new ExerciseQueryResult.Error<String>(exception));
                }
            }
        });
    }

    public void insertExercise(FitnessDatabase database,IViewModel<Exercise> model, Exercise exercise, String workoutDay){
        this.requestInsertExercise(database, model, exercise, workoutDay, new IExercisesQueryCallback() {
            @Override
            public void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success) {
                notifyDataSetChange(model,success.resultBody());
            }

            @Override
            public void onError(ExerciseQueryResult.Error<String> error) {
                Log.e(MainActivity.TAG, "Error: "+ error.getErrorMessage());
            }
        });
    }

}
