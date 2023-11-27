package uk.ac.aston.cs3mdd.fitnessapp.exercises.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExerciseDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExerciseDialogFragment extends DialogFragment {

    private List<Exercise> exercises;

    private CharSequence[] sequences;

    private Context context;

    public static AddExerciseDialogFragment instance;

    private ExerciseViewModel exerciseViewModel;

    private String selectedDay;

    private Exercise selectedExercise;

    public interface AddExerciseDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog, Exercise exercise, String day);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private AddExerciseDialogListener listener;
    private AddExerciseDialogFragment(List<Exercise> exercises, Context ctx,ExerciseViewModel model, String day){
        this.exercises = exercises;
        this.sequences = new CharSequence[exercises.size()];
        this.context = ctx;
        this.exerciseViewModel = model;
        this.selectedDay = day;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setSingleChoiceItems(this.toCharSequence(this.exercises), 0, new DialogInterface.OnClickListener() {
            private CharSequence selectedItem;



            private String day;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem = sequences[which];
                selectedExercise = exercises.get(which);
            }
        });
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //When the user press confirm, it will add the exercise as well as it details to the database
                listener.onDialogPositiveClick(AddExerciseDialogFragment.this,selectedExercise , selectedDay);
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss the dialog if the user presses cancel
                listener.onDialogNegativeClick(AddExerciseDialogFragment.this);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddExerciseDialogListener) this.context;

        }catch (ClassCastException ex){
            throw new ClassCastException(this.context.toString() + " must implements AddExerciseDialogListener");
        }
    }

    private CharSequence[] toCharSequence(List<Exercise> exercises){
        for(int sequenceIndex = 0; sequenceIndex < this.sequences.length; sequenceIndex++){
            this.sequences[sequenceIndex] = exercises.get(sequenceIndex).getName();
        }
        return sequences;
    }

    public static AddExerciseDialogFragment getInstance(List<Exercise> exercises, Context context, ExerciseViewModel model, String day){
        if(instance == null){
            instance = new AddExerciseDialogFragment(exercises,context,model, day);
        }
        return instance;
    }


}