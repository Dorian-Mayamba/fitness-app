package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentCreateWorkoutPlanBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.BodyPartsAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.BodyPartViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.observers.TargetObserver;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners.TargetItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateWorkoutPlanFragment extends Fragment {

    private FragmentCreateWorkoutPlanBinding binding;
    private BodyPartViewModel model;

    private BodyPartsAdapter adapter;

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateWorkoutPlanBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(requireActivity()).get(BodyPartViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BodyPartsAdapter(getContext(), R.layout.target_items, model.getAllBodyParts().getValue());
        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        autoCompleteTextView.setAdapter(adapter);
        final AdapterView.OnItemClickListener itemClickListener = new TargetItemClickListener(getContext());
        autoCompleteTextView.setOnItemClickListener(itemClickListener);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesService service = retrofit.create(ExercisesService.class);
        ExerciseRepository repository = new ExerciseRepository(service);
        model.requestBodyParts(repository);
        final Observer<List<String>> targetObserver = new TargetObserver(adapter);
        model.getAllBodyParts().observe(getViewLifecycleOwner(), targetObserver);
    }
}