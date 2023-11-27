package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.callbacks;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.results.ExerciseQueryResult;

public interface IExercisesQueryCallback {

    void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success);

    void onError(ExerciseQueryResult.Error<String> error);

}
