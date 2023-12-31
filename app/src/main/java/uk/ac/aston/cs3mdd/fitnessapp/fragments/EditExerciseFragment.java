package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.Arrays;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidDay;
import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidNumber;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentEditExerciseFragmentBinding;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseErrorDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnEditConfirmListener;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditExerciseFragment extends Fragment implements Validator.ValidationListener, View.OnFocusChangeListener, AutoCompleteTextView.Validator {
    FragmentEditExerciseFragmentBinding binding;

    private TextView exerciseName;

    private ImageView exerciseImage;

    private ImageView backButton;

    @NotEmpty
    @ValidNumber
    private TextInputEditText setEditText;

    @NotEmpty
    @ValidNumber
    private TextInputEditText repEditText;

    private Validator validator;
    private Button confirmButton;
    private Exercise exercise;

    private String [] days;

    @NotEmpty
    @ValidDay
    private AutoCompleteTextView dayView;

    private OnEditConfirmListener editConfirmListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        days = DayUtil.getDays();
        exercise = EditExerciseFragmentArgs.fromBundle(getArguments()).getExercise();
        binding = FragmentEditExerciseFragmentBinding.inflate(inflater,container,false);
        exerciseImage = binding.exerciseImage;
        backButton = binding.backBtnImg;
        setEditText = binding.numSetField;
        repEditText = binding.numRepsField;
        exerciseName = binding.exerciseName;
        confirmButton = binding.confirmButton;
        dayView = binding.workoutDay;
        dayView.setValidator(this);
        dayView.setOnFocusChangeListener(this);
        dayView.setAdapter(new ArrayAdapter<>(getContext(),R.layout.target_items,days));
        dayView.setText(exercise.getWorkoutDay());
        validator = new Validator(this);
        validator.setValidationListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseName.setText(exercise.getExerciseName());
        setEditText.setText(Integer.toString(exercise.getNumberOfSets()));
        repEditText.setText(Integer.toString(exercise.getNumberOfReps()));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(EditExerciseFragment.this)
                        .navigate(R.id.action_EditExerciseFragment_to_create_workout_plan);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        exerciseImage.setColorFilter(ColorFilterCreator.createColorFilter(0.65f));
        Glide.with(view)
                .asGif()
                .load(exercise.getExerciseImg())
                .into(exerciseImage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            editConfirmListener = (OnEditConfirmListener) context;
        }catch (ClassCastException ex){
            throw new IllegalStateException("The class "+ context.toString() +  " should implement the OnEditExerciseListener"
                    );
        }
        super.onAttach(context);
    }

    @Override
    public void onValidationSucceeded() {
        editConfirmListener.OnEditExercise(exercise, Integer.parseInt(setEditText.getText().toString()), Integer.parseInt(repEditText.getText().toString()), dayView.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error:errors){
            View view = error.getView();
            if (view instanceof TextInputEditText){
                ((TextInputEditText)view).setError(error.getCollatedErrorMessage(getContext()));
            }
            new AddExerciseErrorDialogFragment(error.getCollatedErrorMessage(getContext())).show(getParentFragmentManager(), "ADD_EXERCISE_ERROR");
            break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof AutoCompleteTextView){
            ((AutoCompleteTextView)v).performValidation();
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
}