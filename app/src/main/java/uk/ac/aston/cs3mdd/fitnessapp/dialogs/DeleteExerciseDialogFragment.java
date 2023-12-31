package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentDeleteExerciseDialogBinding;

public class DeleteExerciseDialogFragment extends DialogFragment {

    FragmentDeleteExerciseDialogBinding binding;

    private LayoutInflater inflater;


    private Exercise exercise;
    public interface DeleteDialogListener{
        void onCancelDeletePressed(DialogFragment dialogFragment);
        void onConfirmDeletePressed(DialogFragment dialogFragment, Exercise exercise);
    }

    public DeleteExerciseDialogFragment(Exercise exercise){
        this.exercise = exercise;
    }
    private DeleteDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        binding = FragmentDeleteExerciseDialogBinding.inflate(inflater, null,false);
        binding.message.setText("Delete exercise "+exercise.getExerciseName() + "?\n\nthis action cannot be undone");
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelDeletePressed(DeleteExerciseDialogFragment.this);
            }
        });
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmDeletePressed(DeleteExerciseDialogFragment.this, exercise);
            }
        });
        builder.setTitle("Delete exercise "+exercise.getExerciseName()+"?");
        builder.setView(binding.getRoot());
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            listener = (DeleteDialogListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The context class "+context.toString() + " must implement the DeleteDialogListener interface");
        }
        super.onAttach(context);
    }
}