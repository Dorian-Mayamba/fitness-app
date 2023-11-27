package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.executors;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ExerciseQueryExecutor implements Executor {

    @Inject
    ExerciseQueryExecutor(){}
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
