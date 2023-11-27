package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.models;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.repositories.ExercisesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.generics.AbstractViewModel;

public class ExerciseViewModel extends AbstractViewModel<Exercise> {
    public void addExercise(FitnessDatabase database, ExercisesRepository repository, Exercise exercise, String workoutDay){
        repository.insertExercise(database,this,exercise,workoutDay);
    }
}
