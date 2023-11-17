package uk.ac.aston.cs3mdd.fitnessapp.exercises.services;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.collections.ExerciseList;

public interface ExercisesService {
    @GET("api/v2/exercise")
    Call<ExerciseList> getExercises();
}
