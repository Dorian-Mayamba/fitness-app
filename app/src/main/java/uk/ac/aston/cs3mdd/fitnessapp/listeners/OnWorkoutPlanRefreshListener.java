package uk.ac.aston.cs3mdd.fitnessapp.listeners;

import java.io.IOException;

public interface OnWorkoutPlanRefreshListener {
    void onWorkoutPlanFragmentRefresh(String day) throws IOException;
}
