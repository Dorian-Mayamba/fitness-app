package uk.ac.aston.cs3mdd.fitnessapp.exercises.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

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
}
