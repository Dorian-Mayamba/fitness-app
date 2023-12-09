package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentManageWorkoutPlanBinding;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.BodyPartsAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.database.observers.ExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.EmptyDayDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.EmptyFieldDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.InvalidDayDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.models.BodyPartViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.models.BodyPartsExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.observers.ExerciseFromBodyPartObserver;
import uk.ac.aston.cs3mdd.fitnessapp.observers.TargetObserver;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.TargetItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageWorkoutPlanFragment extends Fragment {

    private FragmentManageWorkoutPlanBinding binding;
    private BodyPartViewModel model;
    private ExerciseViewModel dbExerciseViewModel;

    private FitnessDatabase fitnessDatabase;

    private BodyPartsAdapter adapter;

    private EmptyFieldDialogFragment emptyFieldDialogFragment;
    private AutoCompleteTextView autoCompleteTextView;

    private List<Exercise> exercises;

    private BodyPartsExercisesViewModel bodyPartsExercisesViewModel;

    private EditText dayEditText;

    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private EmptyDayDialogFragment emptyDayDialogFragment;

    private InvalidDayDialogFragment invalidDayDialogFragment;

    private RecyclerView recyclerView;

    private ExerciseAdapter exerciseAdapter;

    private ExercisesService service;

    private Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManageWorkoutPlanBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(requireActivity()).get(BodyPartViewModel.class);
        this.emptyFieldDialogFragment = new EmptyFieldDialogFragment(getContext());
        this.emptyDayDialogFragment = new EmptyDayDialogFragment();
        this.invalidDayDialogFragment = new InvalidDayDialogFragment();
        this.dbExerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        this.exercises = new ArrayList<>();
        this.bodyPartsExercisesViewModel = new ViewModelProvider(requireActivity()).get(BodyPartsExercisesViewModel.class);
        dayEditText = binding.workoutDay;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.exercises_list_view);
        exerciseAdapter = new ExerciseAdapter(getContext(), dbExerciseViewModel.getAllElements().getValue(), getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ExercisesService.class);
        ExerciseRepository repository = new ExerciseRepository(service);
        model.requestBodyParts(repository);
        adapter = new BodyPartsAdapter(getContext(), R.layout.target_items, model.getAllBodyParts().getValue());
        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        autoCompleteTextView.setAdapter(adapter);
        final AdapterView.OnItemClickListener itemClickListener = new TargetItemClickListener(getContext(), bodyPartsExercisesViewModel, repository);
        autoCompleteTextView.setOnItemClickListener(itemClickListener);
        TargetItemClickListener listener = (TargetItemClickListener) autoCompleteTextView.getOnItemClickListener();
        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {

            private String day;

            @Override
            public void onClick(View v) {
                if (listener.getSelectedItem() != null) {
                    // Checks if the user has selected a body part to workout
                    this.day = binding.workoutDay.getText().toString();
                    if (this.day.isEmpty()) {
                        //True if the user has left the day field empty
                        emptyDayDialogFragment.show(getActivity().getSupportFragmentManager(), "EMPTY_DAY");
                        return;
                    } else {
                        //Checks if the day is valid
                        char upper = Character.toUpperCase(day.charAt(0));
                        day = upper + day.substring(1);
                        Log.i(MainActivity.TAG, day);
                        boolean isValidDay = false;
                        for (String targetDay : days) {
                            isValidDay = targetDay.equals(this.day);
                            if (isValidDay) break;
                        }
                        if (!isValidDay) {
                            invalidDayDialogFragment.show(getActivity().getSupportFragmentManager(), "INVALID_DAY");
                            return;
                        }
                    }
                    AddExerciseDialogFragment dialogFragment = AddExerciseDialogFragment.getInstance(exercises, getContext(), dbExerciseViewModel, this.day);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "Exercises");
                } else {
                    emptyFieldDialogFragment.show(getActivity().getSupportFragmentManager(), "EMPTY_FIELD");
                }
            }
        });
        final Observer<List<String>> targetObserver = new TargetObserver(adapter);
        final Observer<List<Exercise>> exerciseObserver = new ExerciseFromBodyPartObserver(exercises);
        model.getAllBodyParts().observe(getViewLifecycleOwner(), targetObserver);
        bodyPartsExercisesViewModel.getAllExercises().observe(getViewLifecycleOwner(), exerciseObserver);
        dbExerciseViewModel.getAllElements().observe(getViewLifecycleOwner(), new ExercisesObserver(exerciseAdapter));
    }
}