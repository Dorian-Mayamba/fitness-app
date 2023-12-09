package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.models.ExerciseViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.AddExerciseAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.ExerciseItemClickListener;

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
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View addExerciseView = inflater.inflate(R.layout.fragment_add_exercise_dialgog, null);
        RecyclerView view = addExerciseView.findViewById(R.id.add_exercise_view);
        ExerciseItemClickListener exerciseItemClickListener = new ExerciseItemClickListener() {
            @Override
            public void onAddExerciseClick(Exercise newExercise) {
                listener.onDialogPositiveClick(AddExerciseDialogFragment.this,newExercise,selectedDay);
            }
        };
        AddExerciseAdapter adapter = new AddExerciseAdapter(exercises, this.context,exerciseItemClickListener);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this.context));
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss the dialog if the user presses cancel
                listener.onDialogNegativeClick(AddExerciseDialogFragment.this);
            }
        })
                .setView(addExerciseView);
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