package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.database.repositories.ExercisesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.ActivityMainBinding;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.DeleteExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.EditExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AddExerciseDialogFragment.AddExerciseDialogListener,
        DeleteExerciseDialogFragment.DeleteDialogListener,
        EditExerciseDialogFragment.EditDialogListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        BottomNavigationView navigationView = binding.bottomNavigation;
        //NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
        model = new ViewModelProvider(this).get(ExerciseViewModel.class);
        if (executor != null) {
            Log.i(MainActivity.TAG, "Successfully inject executor");
        }
        this.exercisesRepository = new ExercisesRepository(executor, handler);
        exercisesRepository.loadExercises(database, model);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Exercise exercise, String day) {
        //When the user confirms the exercise to add
        uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise exerciseToAdd = new uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise();
        exerciseToAdd.setExerciseImg(exercise.getGifUrl());
        String instructions = "";
        for (String i : exercise.getInstructions()) {
            instructions += i + '\n';
        }
        exerciseToAdd.setExerciseInstructions(instructions);
        exerciseToAdd.setExerciseName(exercise.getName());
        exerciseToAdd.setBodyPart(exercise.getBodyPart());
        exercisesRepository.insertExercise(database, model, exerciseToAdd, day);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
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
    }


    @Override
    public void onEditConfirm(DialogFragment fragment,
                              uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise exercise,
                              Exercise chosenExercise) {
        exercise.setExerciseImg(chosenExercise.getGifUrl());
        exercise.setExerciseName(chosenExercise.getName());
        String instructions = "";
        List<String> exerciseInstructions = chosenExercise.getInstructions();
        for (String i : exerciseInstructions) {
            instructions += i + '\n';
        }
        exercise.setExerciseInstructions(instructions);
        exercisesRepository.updateExercise(database, model, exercise);
        fragment.dismiss();
    }

    @Override
    public void onEditCancel(DialogFragment fragment) {
        fragment.dismiss();
    }

}