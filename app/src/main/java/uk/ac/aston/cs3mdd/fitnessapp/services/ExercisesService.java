package uk.ac.aston.cs3mdd.fitnessapp.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;

public interface ExercisesService {
    @Headers({
            "X-RapidAPI-Key: 3a903baa54mshcec096be2d5fb99p1f52acjsne7fff04997ad",
            "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    })
    @GET("/exercises")
    Call<List<Exercise>> getExercises();

    @Headers({
            "X-RapidAPI-Key: 3a903baa54mshcec096be2d5fb99p1f52acjsne7fff04997ad",
            "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    })
    @GET("/exercises/bodyPartList")
    Call<List<String>> getBodyParts();

    @Headers({
            "X-RapidAPI-Key: 3a903baa54mshcec096be2d5fb99p1f52acjsne7fff04997ad",
            "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    })
    @GET("/exercises/bodyPart/{bodyPart}")
    Call<List<Exercise>> getExercisesFromBodyPart(@Path("bodyPart") String bodyPart);

    @Headers({
            "X-RapidAPI-Key: 3a903baa54mshcec096be2d5fb99p1f52acjsne7fff04997ad",
            "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    })
    @GET("/exercises/exercise/{id}")
    Call<Exercise> getExerciseFromId(@Path("id") String exerciseId);

}
