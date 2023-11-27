package uk.ac.aston.cs3mdd.fitnessapp.exercises.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.database.repositories.ExercisesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentEditExerciseDialogBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.BodyPartsAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.BodyPartViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.observers.TargetObserver;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

public class EditExerciseDialogFragment extends DialogFragment {

    private Exercise exercise;

    @Inject
    Executor executor;

    @Inject
    Handler handler;

    @Inject
    FitnessDatabase database;

    Retrofit retrofit;

    private ExercisesRepository repository;

    private ExerciseRepository exerciseRepository;

    private ExerciseViewModel model;

    public interface EditDialogListener{
        void onEditConfirm(DialogFragment fragment, Exercise exercise);
        void onEditCancel(DialogFragment fragment);
    }

    EditDialogListener listener;

    public EditExerciseDialogFragment(Exercise exercise){
        this.exercise = exercise;
    }

    private FragmentEditExerciseDialogBinding binding;

    private AutoCompleteTextView textView;

    private BodyPartsAdapter adapter;

    private BodyPartViewModel bodyPartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.repository = new ExercisesRepository(executor,handler);
        this.model = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        bodyPartViewModel = new ViewModelProvider(requireActivity()).get(BodyPartViewModel.class);
        binding = FragmentEditExerciseDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.edit_dialog_view);
        adapter = new BodyPartsAdapter(getContext(), R.layout.target_items,bodyPartViewModel.getAllBodyParts().getValue());
        textView.setAdapter(adapter);
        final Observer<List<String>> bodyPartObserver = new TargetObserver(adapter);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesService exercisesService = retrofit.create(ExercisesService.class);
        bodyPartViewModel.requestBodyParts(exerciseRepository);
        bodyPartViewModel.getAllBodyParts().observe(getViewLifecycleOwner(), bodyPartObserver);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onEditCancel(EditExerciseDialogFragment.this);
                    }
                })
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onEditConfirm(EditExerciseDialogFragment.this,exercise);
                    }
                })
                .setView(R.layout.fragment_edit_exercise_dialog);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            listener = (EditDialogListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The context class "+ context.toString() + " must implement the EditDialogListener interface");
        }
        super.onAttach(context);
    }
}
