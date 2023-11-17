package uk.ac.aston.cs3mdd.fitnessapp.exercises.collections;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

public class ExerciseList {
    List<Exercise> results;

    public ExerciseList(){
        results = new ArrayList<>();
    }

    public void setResults(List<Exercise> results) {
        this.results = results;
    }

    public List<Exercise> getResults() {
        return results;
    }
}
