package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentGymsBinding;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.models.ExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.observers.ExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;

public class GymFragment extends Fragment {

    private FragmentGymsBinding binding;

    private ExercisesViewModel model;

    private RecyclerView recyclerView;

    private ExerciseAdapter exerciseAdapter;
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
        recyclerView = view.findViewById(R.id.recyclerview);
        exerciseAdapter = new ExerciseAdapter(getContext(), model.getAllExercises().getValue());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();

        ExercisesService exercisesService = retrofit.create(ExercisesService.class);
        model.requestExercises(new ExerciseRepository(exercisesService));
        final Observer<List<Exercise>> exercisesObserver = new ExercisesObserver(exerciseAdapter);
        model.getAllExercises().observe(getViewLifecycleOwner(), exercisesObserver);
        super.onViewCreated(view, savedInstanceState);
    }
}
