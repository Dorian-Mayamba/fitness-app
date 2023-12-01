package uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories;

import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

public class ExerciseRepository {

    private ExercisesService exercisesService;
    public ExerciseRepository(ExercisesService exercisesService){
        this.exercisesService = exercisesService;
    }

    public Call<List<Exercise>> getExercises(){
        return this.exercisesService.getExercises();
    }

    public Call<List<String>> getBodyParts(){
        return this.exercisesService.getBodyParts();
    }

    public Call<List<Exercise>> getExercisesFromBodyPart(String bodyPart){
        return this.exercisesService.getExercisesFromBodyPart(bodyPart);
    }

    public Call<Exercise> getExerciseById(int exerciseId){
        return this.exercisesService.getExerciseFromId(exerciseId);
    }



}
