package uk.ac.aston.cs3mdd.fitnessapp.database.repositories;

import android.os.Handler;

import java.util.concurrent.Executor;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.WorkoutPlan;
import uk.ac.aston.cs3mdd.fitnessapp.generics.AbstractRepository;

public class WorkoutPlanRepository extends AbstractRepository<WorkoutPlan> {

    public WorkoutPlanRepository(Executor executor, Handler handler) {
        super(executor, handler);
    }
}
