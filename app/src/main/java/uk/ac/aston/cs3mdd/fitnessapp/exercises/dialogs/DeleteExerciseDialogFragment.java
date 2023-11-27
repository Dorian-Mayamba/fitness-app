package uk.ac.aston.cs3mdd.fitnessapp.exercises.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;

public class DeleteExerciseDialogFragment extends DialogFragment {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Delete exercise: "+ exercise.getExerciseName() + "?")
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onConfirmDeletePressed(DeleteExerciseDialogFragment.this,exercise);
                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancelDeletePressed(DeleteExerciseDialogFragment.this);
                    }
                });
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