package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidDay;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidNumber;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.database.repositories.ExercisesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.ActivityMainBinding;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseErrorDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.DeleteExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.OpeningHoursDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.fragments.WelcomeFragment;
import uk.ac.aston.cs3mdd.fitnessapp.generics.IViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnAddExerciseConfirmListener;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnEditConfirmListener;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnWorkoutDayChangeListener;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnWorkoutPlanRefreshListener;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobsandgeeks.saripaar.Validator;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements DeleteExerciseDialogFragment.DeleteDialogListener, OnEditConfirmListener,
        AddExerciseErrorDialogFragment.OnOkClickListerner, OnAddExerciseConfirmListener, OnWorkoutDayChangeListener,
        OnWorkoutPlanRefreshListener, WelcomeFragment.OnAppStartListener,
        OpeningHoursDialogFragment.OnDismissClickListener, NavController.OnDestinationChangedListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private Toolbar toolbar;

    public static final String TAG = "Fitness";
    private ExerciseViewModel model;
    private ExercisesRepository exercisesRepository;
    @Inject
    Executor executor;
    @Inject
    Handler handler;

    private NavController navController;

    @Inject
    FitnessDatabase database;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Validator.registerAnnotation(ValidDay.class);
        Validator.registerAnnotation(ValidNumber.class);
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(this);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        navigationView = binding.bottomNavigation;
        NavigationUI.setupWithNavController(navigationView, navController);
        model = new ViewModelProvider(this).get(ExerciseViewModel.class);
        if (executor != null) {
            Log.i(MainActivity.TAG, "Successfully inject executor");
        }
        this.exercisesRepository = new ExercisesRepository(executor, handler);
        try {
            loadExercises(exercisesRepository, database, model, DayUtil.getCurrentDayDisplayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCancelDeletePressed(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
    }

    @Override
    public void onConfirmDeletePressed(DialogFragment dialogFragment,
                                       uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise
                                               exercise) {
        exercisesRepository.deleteExercise(database, model, exercise);
        Toast.makeText(this, "The exercise " + exercise.getExerciseName() +
                " Has been deleted", Toast.LENGTH_LONG).show();
        dialogFragment.dismiss();
    }

    private void loadExercises(ExercisesRepository exercisesRepository, FitnessDatabase database, IViewModel<uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise> model, String day) throws IOException {
        exercisesRepository.loadExercises(database, model, day);
    }


    @Override
    public void OnEditExercise(uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise exercise, int numberOfSets, int numberOfReps, String day) {
        if (exercise.getNumberOfSets() == numberOfSets && exercise.getNumberOfReps() == numberOfReps && exercise.getWorkoutDay().equals(day)) {
            Toast.makeText(this, "Please modify your exercise", Toast.LENGTH_LONG).show();
            return;
        }
        exercise.setNumberOfReps(numberOfReps);
        exercise.setNumberOfSets(numberOfSets);
        exercise.setWorkoutDay(day);
        exercisesRepository.updateExercise(database, model, exercise);
        Toast.makeText(this, "The exercise " + exercise.getExerciseName() + " has been modified", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOkClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
    }

    @Override
    public void onAddConfirm(Exercise exercise, int numberOfSets, int numberOfReps, String day) {
        uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise exerciseToAdd = new uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise();
        for (uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise e : model.getAllElements().getValue()) {
            if (e.getExerciseName().equals(exercise.getName())) {
                new AddExerciseErrorDialogFragment("The exercise " + exercise.getName() + " is already saved in your plans").show(getSupportFragmentManager(), "ADD_EXERCISE_ERROR");
                return;
            }
        }
        exerciseToAdd.setExerciseImg(exercise.getGifUrl());
        String instructions = "";
        for (String i : exercise.getInstructions()) {
            instructions += i + '\n';
        }
        exerciseToAdd.setExerciseInstructions(instructions);
        exerciseToAdd.setExerciseName(exercise.getName());
        exerciseToAdd.setBodyPart(exercise.getBodyPart());
        exerciseToAdd.setNumberOfSets(numberOfSets);
        exerciseToAdd.setNumberOfReps(numberOfReps);
        exerciseToAdd.setExerciseId(exercise.getId());
        exerciseToAdd.setWorkoutDay(day);
        exercisesRepository.insertExercise(database, model, exerciseToAdd, day);
        Toast.makeText(this, "The exercise: " + exerciseToAdd.getExerciseName() + " has been saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWorkoutDayChange(String day) throws IOException {
        loadExercises(exercisesRepository, database, model, day);
    }

    @Override
    public void onWorkoutPlanFragmentRefresh(String day) throws IOException {
        loadExercises(exercisesRepository, database, model, day);
    }

    @Override
    public void onAppStarted() {
        navigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismissClick(DialogFragment fragment) {
        fragment.dismiss();
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
        if (navigationView != null) {
            //Handles the destination changed event only if the bottom navigation view is not null
            if (!isPrincipalFragment(navDestination)) {
                navigationView.setVisibility(View.GONE);
            } else {
                navigationView.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isPrincipalFragment(@NonNull NavDestination destination) {
        return destination.getId() ==
                R.id.HomeFragment || destination.getId()
                == R.id.GymFragment || destination.getId() == R.id.ManageMyPlansFragment
                || destination.getId() == R.id.LocalGymFragment;
    }
}