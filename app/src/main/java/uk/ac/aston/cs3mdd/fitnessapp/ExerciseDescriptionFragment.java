package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentExerciseDescriptionBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners.BackButtonOnClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDescriptionFragment extends Fragment {

    FragmentExerciseDescriptionBinding binding;

    private Exercise exercise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExerciseDescriptionBinding.inflate(getLayoutInflater());
        exercise = ExerciseDescriptionFragmentArgs.fromBundle(getArguments()).getExercise();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String instructions = "";
        for(String i:exercise.getInstructions()){
            instructions += i + '\n';
        }
        binding.exerciseDescription.setText(instructions);
        binding.backBtn.setOnClickListener(new BackButtonOnClickListener(this));
    }
}