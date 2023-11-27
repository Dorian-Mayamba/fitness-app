package uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners;

import android.view.View;

import androidx.navigation.Navigation;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.fragments.GymFragmentDirections;

public class MoreInfoButtonClickedListener implements View.OnClickListener{

    private Exercise exercise;

    public MoreInfoButtonClickedListener(Exercise info){
        this.exercise = info;
    }
    @Override
    public void onClick(View v) {
        //Implement this method
        GymFragmentDirections.ActionGymFragmentToExerciseDescriptionFragment action
                = GymFragmentDirections.actionGymFragmentToExerciseDescriptionFragment(this.exercise);
        Navigation.findNavController(v)
                .navigate(action);
    }
}
