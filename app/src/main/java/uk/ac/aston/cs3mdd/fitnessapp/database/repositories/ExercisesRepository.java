package uk.ac.aston.cs3mdd.fitnessapp.database.repositories;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;
import uk.ac.aston.cs3mdd.fitnessapp.database.callbacks.IExercisesQueryCallback;
import uk.ac.aston.cs3mdd.fitnessapp.database.relationships.WorkoutPlansWithExercises;
import uk.ac.aston.cs3mdd.fitnessapp.database.results.ExerciseQueryResult;
import uk.ac.aston.cs3mdd.fitnessapp.generics.AbstractRepository;
import uk.ac.aston.cs3mdd.fitnessapp.generics.IViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;

public class ExercisesRepository extends AbstractRepository<Exercise> {
    public ExercisesRepository(Executor executor, Handler handler) {
        super(executor, handler);
    }

    private void requestLoadExercises(FitnessDatabase database, IViewModel<Exercise> model, IExercisesQueryCallback callback, String day) throws IOException {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                //android.os.Debug.waitForDebugger();
                Log.i(MainActivity.TAG, "Load exercise request initialised");
                try{
                    List<Exercise> exercises;
                    WorkoutPlansWithExercises planWithExercises = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises(day);
                    if (planWithExercises == null){
                        WorkoutPlan plan = new WorkoutPlan();
                        plan.setDay(day);
                        database.workoutPlanDao().createWorkoutPlan(plan);
                        exercises = new ArrayList<>();
                    }else{
                        exercises = planWithExercises.getExercises();

                    }
                    ExercisesService service = ServiceProvider.getExerciseService();
                    uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise exercise = null;
                    if (exercises.size() > 0){
                        for (Exercise e:exercises){
                            exercise = service.getExerciseFromId(e.getExerciseId()).execute().body();
                            if (exercise != null && !exercise.getGifUrl().equals(e.getExerciseImg())){
                                e.setExerciseId(exercise.getGifUrl());
                            }
                        }
                    }
                    callback.onSuccess(new ExerciseQueryResult.Success<List<Exercise>>(exercises));
                }catch(Exception exception){
                    callback.onError(new ExerciseQueryResult.Error<String>(exception));
                }
            }
        });
    }

    public void loadExercises(FitnessDatabase database, IViewModel<Exercise> model, String day) throws IOException {
        requestLoadExercises(database, model, new IExercisesQueryCallback(){

            @Override
            public void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success) {
                Log.i(MainActivity.TAG, "Notifying the data change to the observer");
                notifyDataSetChange(model,success.resultBody());
            }

            @Override
            public void onError(ExerciseQueryResult.Error<String> error) {
                Log.e(MainActivity.TAG + " LoadExercises error", "Error: "+ error.getErrorMessage());
            }
        }, day);
    }

    private void requestInsertExercise(FitnessDatabase database, IViewModel<Exercise> model, Exercise exercise, String workoutDay, IExercisesQueryCallback callback){
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background task
                String exerciseDay = exercise.getWorkoutDay();
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
                    List<Exercise> exercises = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises(exerciseDay).getExercises();
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
                Log.e(MainActivity.TAG + " Insert exercise error", "Error: "+ error.getErrorMessage());
            }
        });
    }

    private void requestExerciseDelete(FitnessDatabase database, IViewModel<Exercise> model, Exercise exercise, IExercisesQueryCallback callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String exerciseDay = exercise.getWorkoutDay();
                    database.exerciseDao().deleteExercise(exercise);
                    List<Exercise> exercises = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises(exerciseDay).getExercises();
                    callback.onSuccess(new ExerciseQueryResult.Success<>(exercises));
                }catch (Exception e){
                    callback.onError(new ExerciseQueryResult.Error<>(e));
                }
            }
        });
    }
    public void deleteExercise(FitnessDatabase database, IViewModel<Exercise> model, Exercise exercise){
        requestExerciseDelete(database,model,exercise,new IExercisesQueryCallback(){

            @Override
            public void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success) {
                notifyDataSetChange(model, success.resultBody());
            }

            @Override
            public void onError(ExerciseQueryResult.Error<String> error) {
                Log.e(MainActivity.TAG + " Delete exercise error", "Could not delete exercise: "+exercise.getExerciseName() + " "+ error.getErrorMessage());
            }
        });
    }

    public void updateExercise(FitnessDatabase database, IViewModel<Exercise> model,Exercise exerciseToModify){
        requestUpdateExercise(database, model, exerciseToModify, new IExercisesQueryCallback() {
            @Override
            public void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success) {
                notifyDataSetChange(model, success.resultBody());
            }

            @Override
            public void onError(ExerciseQueryResult.Error<String> error) {
                Log.i(MainActivity.TAG + " UpdateExercise error", "Error: "+ error.getErrorMessage());
            }
        });
    }

    private void requestUpdateExercise(FitnessDatabase database,IViewModel<Exercise> model, Exercise exerciseToModify, IExercisesQueryCallback callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String exerciseDay = exerciseToModify.getWorkoutDay();
                    database.exerciseDao().updateExercise(exerciseToModify);
                    List<Exercise> exercises = database.workoutPlanWithExercisesDao().getWorkoutPlanWithExercises(exerciseDay).getExercises();
                    callback.onSuccess(new ExerciseQueryResult.Success<List<Exercise>>(exercises));
                }catch (Exception exception){
                    callback.onError(new ExerciseQueryResult.Error<String>(exception));
                }
            }
        });
    }

}
