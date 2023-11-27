package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.repositories.ExercisesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.ActivityMainBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.dialogs.AddExerciseDialogFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.Executor;

import javax.inject.Inject;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements AddExerciseDialogFragment.AddExerciseDialogListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public static final String TAG = "Fitness";

    private ExerciseViewModel model;
    private ExercisesRepository exercisesRepository;
    @Inject
    Executor executor;
    @Inject
    Handler handler;

    @Inject
    FitnessDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        setSupportActionBar(binding.toolBar);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(binding.toolBarLayout,binding.toolBar,navController,appBarConfiguration);
        model = new ViewModelProvider(this).get(ExerciseViewModel.class);
        if(executor!=null){
            Log.i(MainActivity.TAG, "Successfully inject executor");
        }
        this.exercisesRepository = new ExercisesRepository(executor,handler);
        this.exercisesRepository.loadExercises(database, model);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_content_main);
        return NavigationUI.onNavDestinationSelected(item,navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Exercise exercise, String day) {
        //When the user confirms the exercise to add
        uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise exerciseToAdd = new uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise();
        exerciseToAdd.setExerciseImg(exercise.getGifUrl());
        String instructions = "";
        for(String i:exercise.getInstructions()){
            instructions+= i + '\n';
        }
        exerciseToAdd.setExerciseInstructions(instructions);
        exerciseToAdd.setExerciseName(exercise.getName());
        exerciseToAdd.setBodyPart(exercise.getBodyPart());
        this.exercisesRepository.insertExercise(database,model,exerciseToAdd,day);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}