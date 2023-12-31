package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidDay;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidNumber;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExerciseDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExerciseDialogFragment extends DialogFragment implements Validator.ValidationListener, AutoCompleteTextView.Validator, View.OnFocusChangeListener {



    public interface AddExerciseDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog, Exercise exercise, String day, int numberOfSet, int numberOfReps);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private Exercise exercise;

    private String [] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @NotEmpty(message = "Please enter a day")
    @ValidDay
    private AutoCompleteTextView dayView;

    private Validator validator;

    @NotEmpty(message = "Please enter a number of sets")
    @ValidNumber(message = "Please enter a number")
    private TextInputEditText numberOfSetsField;

    @NotEmpty(message = "Please enter a number of reps")
    @ValidNumber(message = "Please enter a number")
    private TextInputEditText numberOfRepsField;

    private ImageView exerciseImage;

    private TextView bodyPartView;

    private Button confirmButton;

    private Button cancelButton;
    public AddExerciseDialogFragment(Exercise exercise){
        this.exercise = exercise;
    }

    private AddExerciseDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View addExerciseDialogView = inflater.inflate(R.layout.fragment_add_exercise_dialgog, null, false);
        dayView = addExerciseDialogView.findViewById(R.id.workout_day);
        dayView.setAdapter(new ArrayAdapter<String>(requireContext(), R.layout.target_items,days));
        dayView.setThreshold(1);
        dayView.setValidator(this);
        numberOfSetsField = addExerciseDialogView.findViewById(R.id.num_set_field);
        numberOfRepsField = addExerciseDialogView.findViewById(R.id.num_reps_field);
        bodyPartView = addExerciseDialogView.findViewById(R.id.dialog_body_part_text);
        bodyPartView.setText(exercise.getBodyPart());
        exerciseImage = addExerciseDialogView.findViewById(R.id.dialog_exercise_img);
        validator = new Validator(this);
        validator.setValidationListener(this);
        confirmButton = addExerciseDialogView.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        cancelButton = addExerciseDialogView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogNegativeClick(AddExerciseDialogFragment.this);
            }
        });
        ColorFilter colorFilter =  ColorFilterCreator.createColorFilter(0.65f);
        exerciseImage.setColorFilter(colorFilter);
        Glide.with(addExerciseDialogView)
                .asGif()
                .load(exercise.getGifUrl())
                .into(exerciseImage);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Add an exercise")
                .setView(addExerciseDialogView);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddExerciseDialogListener) context;

        }catch (ClassCastException ex){
            throw new ClassCastException(context.toString() + " must implements AddExerciseDialogListener");
        }
    }

    @Override
    public void onValidationSucceeded() {
        listener.onDialogPositiveClick(AddExerciseDialogFragment.this,exercise, dayView.getText().toString(),
                Integer.parseInt(numberOfSetsField.getText().toString()), Integer.parseInt(numberOfRepsField.getText().toString()));
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Resources errorRes = getResources();
        try {
            Drawable errorDrawable = Drawable.createFromXml(errorRes, errorRes.getXml(R.xml.ic_error));
            for (ValidationError error:errors){
                if (error.getView() instanceof TextInputEditText){

                    ((TextInputEditText)error.getView()).setError(error.getCollatedErrorMessage(requireContext()), errorDrawable);
                }else if (error.getView() instanceof AutoCompleteTextView){
                    ((AutoCompleteTextView)error.getView()).setError(error.getCollatedErrorMessage(requireContext()),errorDrawable);
                }
                Toast.makeText(getContext(), error.getCollatedErrorMessage(getContext()), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isValid(CharSequence text) {
        Arrays.sort(days);
        boolean textNotEmpty = !text.toString().isEmpty();
        boolean isValidText = Arrays.asList(days).stream().anyMatch((s -> s.equals(text.toString())));
        return textNotEmpty && isValidText;
    }


    @Override
    public CharSequence fixText(CharSequence invalidText) {
        if (invalidText.toString().isEmpty()){
            return "Please enter a workout day";
        }else{
            return "Please enter a valid workout day";
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof AutoCompleteTextView && !hasFocus){
            ((AutoCompleteTextView)v).performValidation();
        }
    }


}