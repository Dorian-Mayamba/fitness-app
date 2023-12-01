package uk.ac.aston.cs3mdd.fitnessapp.exercises.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentEditExerciseDialogBinding;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters.EditExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners.ExerciseItemClickListener;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.models.DialogExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.observers.DialogExercisesObserver;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.repositories.ExerciseRepository;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

public class EditExerciseDialogFragment extends DialogFragment {

    private Exercise exercise;

    public interface EditDialogListener{
        void onEditConfirm(DialogFragment fragment, Exercise exercise, uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise chosenExercise);
        void onEditCancel(DialogFragment fragment);
    }

    EditDialogListener listener;

    public EditExerciseDialogFragment(Exercise exercise){
        this.exercise = exercise;
    }

    private FragmentEditExerciseDialogBinding binding;

    private Retrofit retrofit;

    private ExerciseRepository repository;

    private RecyclerView recyclerView;

    private EditExerciseAdapter adapter;

    private DialogExercisesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditExerciseDialogBinding.inflate(inflater,container,false);
        final Observer<List<uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise>> exercisesObserver
                = new DialogExercisesObserver(adapter);
        viewModel.getAllExercises().observe(getViewLifecycleOwner(), exercisesObserver);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(DialogExercisesViewModel.class);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_exercise_dialog, null);
        ExerciseItemClickListener exerciseItemClickListener = new ExerciseItemClickListener() {

            @Override
            public void onEditExerciseClick(uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise newExercise) {
                listener.onEditConfirm(EditExerciseDialogFragment.this, exercise, newExercise);
            }
        };
        adapter = new EditExerciseAdapter(getContext(), viewModel.getAllExercises().getValue(), exerciseItemClickListener);
        recyclerView = view.findViewById(R.id.edit_dialog_recycle_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesService exercisesService = retrofit.create(ExercisesService.class);
        repository = new ExerciseRepository(exercisesService);
        viewModel.requestExercise(repository,exercise.getBodyPart());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onEditCancel(EditExerciseDialogFragment.this);
                    }
                }).setView(view);
        return builder.create();
    }

    private CharSequence[] toCharSequence(List<uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise> exercises){
        CharSequence[] sequences = new CharSequence[exercises.size()];
        for (int i=0; i < sequences.length; i++){
            sequences[i] = exercises.get(i).getName();
        }
        return sequences;
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
