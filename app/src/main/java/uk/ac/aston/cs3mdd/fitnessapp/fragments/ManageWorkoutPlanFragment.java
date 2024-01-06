package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.adapters.ExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentManageWorkoutPlanBinding;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnWorkoutDayChangeListener;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnWorkoutPlanRefreshListener;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;
import uk.ac.aston.cs3mdd.fitnessapp.database.observers.ExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageWorkoutPlanFragment extends Fragment implements SearchView.OnQueryTextListener {

    private FragmentManageWorkoutPlanBinding binding;
    private ExerciseViewModel dbExerciseViewModel;

    private RecyclerView recyclerView;

    private ExerciseAdapter exerciseAdapter;

    private ExercisesService service;

    private List<Exercise> filteredExercises;

    private SearchView searchView;

    private ImageView leftArrow,rightArrow;

    private TextView workoutDay;

    private int workoutDayIndex;

    private String days [];

    private OnWorkoutDayChangeListener listener;

    private OnWorkoutPlanRefreshListener refreshListener;
    @Override
    public void onAttach(@NonNull Context context) {
        try {
            refreshListener = (OnWorkoutPlanRefreshListener) context;
            listener = (OnWorkoutDayChangeListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The class "+ context.toString() + " should implement the OnWorkoutDayChangeListener interface and \n"+
                    "OnRefreshFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManageWorkoutPlanBinding.inflate(inflater, container, false);
        dbExerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        filteredExercises = new ArrayList<>();
        workoutDayIndex = DayUtil.getDayNum();
        days = DayUtil.getDays();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.exercises_list_view);
        searchView = view.findViewById(R.id.workout_search_view);
        searchView.setOnQueryTextListener(this);
        exerciseAdapter = new ExerciseAdapter(getContext(), dbExerciseViewModel.getAllElements().getValue(), getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutDay = view.findViewById(R.id.workout_plan_day);
        workoutDay.setText(String.format("%s exercises", days[workoutDayIndex]));
        leftArrow = view.findViewById(R.id.left_arrow);
        rightArrow = view.findViewById(R.id.right_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutDayIndex--;
                if (workoutDayIndex < 0){
                    workoutDayIndex = days.length - 1;
                }
                String day = days[workoutDayIndex];
                workoutDay.setText(String.format("%s exercises",day));
                try {
                    listener.onWorkoutDayChange(day);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutDayIndex++;
                if (workoutDayIndex > days.length -1){
                    workoutDayIndex = 0;
                }
                String day = days[workoutDayIndex];
                workoutDay.setText(String.format("%s exercises",day));
                try {
                    listener.onWorkoutDayChange(day);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        service = ServiceProvider.getExerciseService();
        ExerciseRepository repository = new ExerciseRepository(service);
        dbExerciseViewModel.getAllElements().observe(getViewLifecycleOwner(), new ExercisesObserver(exerciseAdapter));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }

    @Override
    public void onResume() {
        workoutDay.setText(String.format("%s exercises",days[workoutDayIndex]));
        try {
            refreshListener.onWorkoutPlanFragmentRefresh(days[workoutDayIndex]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onResume();
    }

    public void filter(String text) {
        if (!filteredExercises.isEmpty()) {
            filteredExercises.clear();
        }
        for (Exercise exercise : dbExerciseViewModel.getAllElements().getValue()) {
            if (exercise.getExerciseName().toLowerCase().contains(text.toLowerCase())) {
                filteredExercises.add(exercise);
            }
        }
        if (filteredExercises.isEmpty()) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
        }
        exerciseAdapter.updateExercisesList(filteredExercises);
    }
}