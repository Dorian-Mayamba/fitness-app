package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.BodyPartsAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentGymsBinding;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.models.BodyPartViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.observers.TargetObserver;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.models.ExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.observers.ExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;

public class GymFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentGymsBinding binding;

    private ExercisesViewModel model;

    private RecyclerView recyclerView;

    private AutoCompleteTextView bodyPartField;

    private ExerciseAdapter exerciseAdapter;

    private BodyPartViewModel bodyPartViewModel;

    private ExercisesService exercisesService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGymsBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(requireActivity()).get(ExercisesViewModel.class);
        bodyPartViewModel = new ViewModelProvider(requireActivity()).get(BodyPartViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(MainActivity.TAG, "View created in "+this.getClass());
        recyclerView = view.findViewById(R.id.recyclerview);
        bodyPartField = view.findViewById(R.id.body_part_field);
        BodyPartsAdapter bodyPartsAdapter = new BodyPartsAdapter(getContext(), R.layout.target_items, bodyPartViewModel.getAllBodyParts().getValue());
        bodyPartField.setAdapter(bodyPartsAdapter);
        bodyPartField.setOnItemClickListener(this);
        exerciseAdapter = new ExerciseAdapter(getContext(), model.getAllExercises().getValue());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exercisesService = ServiceProvider.getExerciseService();
        bodyPartViewModel.requestBodyParts(new ExerciseRepository(exercisesService));
        final Observer<List<String>> bodyPartObserver = new TargetObserver(bodyPartsAdapter);
        bodyPartViewModel.getAllBodyParts().observe(getViewLifecycleOwner(), bodyPartObserver);
        model.requestExercises(new ExerciseRepository(exercisesService));
        final Observer<List<Exercise>> exercisesObserver = new ExercisesObserver(exerciseAdapter);
        model.getAllExercises().observe(getViewLifecycleOwner(), exercisesObserver);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String bodyPart = (String) parent.getItemAtPosition(position);
        model.requestExercisesFromBodyPart(new ExerciseRepository(exercisesService), bodyPart);
    }
}
