package uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import uk.ac.aston.cs3mdd.fitnessapp.R;

public class BackButtonOnClickListener implements View.OnClickListener{

    private Fragment fragment;

    public BackButtonOnClickListener(Fragment f){
        this.fragment = f;
    }
    @Override
    public void onClick(View v) {
        NavHostFragment.findNavController(fragment)
                .navigate(R.id.action_ExerciseDescriptionFragment_to_GymFragment);
    }
}
