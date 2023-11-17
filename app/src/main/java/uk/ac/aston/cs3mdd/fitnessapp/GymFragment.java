package uk.ac.aston.cs3mdd.fitnessapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentGymsBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.ExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.observers.ExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

public class GymFragment extends Fragment {

    private FragmentGymsBinding binding;

    private ExercisesViewModel model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGymsBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(requireActivity()).get(ExercisesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(MainActivity.TAG, "View created in "+this.getClass());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wger.de/")
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();

        ExercisesService exercisesService = retrofit.create(ExercisesService.class);
        model.requestExercises(new ExerciseRepository(exercisesService));
        model.getAllExercises().observe(getViewLifecycleOwner(), new ExercisesObserver());
        super.onViewCreated(view, savedInstanceState);
    }
}
