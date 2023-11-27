package uk.ac.aston.cs3mdd.fitnessapp.database.callbacks;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.results.ExerciseQueryResult;

public interface IExercisesQueryCallback {

    void onSuccess(ExerciseQueryResult.Success<List<Exercise>> success);

    void onError(ExerciseQueryResult.Error<String> error);

}
