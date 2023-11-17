package uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.collections.ExerciseList;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

public class ExerciseRepository {

    private ExercisesService exercisesService;
    public ExerciseRepository(ExercisesService exercisesService){
        this.exercisesService = exercisesService;
    }

    public Call<ExerciseList> getExercises(){
        return this.exercisesService.getExercises();
    }

}
