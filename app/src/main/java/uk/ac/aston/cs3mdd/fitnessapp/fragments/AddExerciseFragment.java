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
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseErrorDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.OnAddExerciseConfirmListener;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentAddExerciseBinding;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;
import uk.ac.aston.cs3mdd.fitnessapp.util.DayUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExerciseFragment extends Fragment implements Validator.ValidationListener, AutoCompleteTextView.Validator, View.OnFocusChangeListener {

    FragmentAddExerciseBinding binding;

    private TextView exerciseName;

    private ImageView exerciseImage;

    private ImageView backButton;

    @NotEmpty
    @ValidDay
    private AutoCompleteTextView dayView;
    @NotEmpty
    @ValidNumber
    private TextInputEditText setEditText;

    @NotEmpty
    @ValidNumber
    private TextInputEditText repEditText;

    private Button confirmButton;
    private Exercise exercise;

    private Validator validator;

    private String [] days = DayUtil.getDays();

    private OnAddExerciseConfirmListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exercise = AddExerciseFragmentArgs.fromBundle(getArguments()).getExercise();
        binding = FragmentAddExerciseBinding.inflate(inflater, container, false);
        exerciseImage = binding.exerciseImage;
        backButton = binding.backBtnImg;
        setEditText = binding.numSetField;
        repEditText = binding.numRepsField;
        exerciseName = binding.exerciseName;
        confirmButton = binding.confirmButton;
        dayView = binding.workoutDay;
        dayView.setAdapter(new ArrayAdapter<String>(requireContext(), R.layout.target_items,days));
        dayView.setThreshold(1);
        dayView.setValidator(this);
        dayView.setOnFocusChangeListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AddExerciseFragment.this)
                        .navigate(R.id.action_AddExerciseFragment_to_GymFragment);
            }
        });
        exerciseName.setText(exercise.getName());
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        exerciseImage.setColorFilter(ColorFilterCreator.createColorFilter(0.65f));
        Glide.with(view)
                .asGif()
                .load(exercise.getGifUrl())
                .into(exerciseImage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            listener = (OnAddExerciseConfirmListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The class " + context.toString() + " should implement the OnAddExerciseConfirmListener interface");
        }
        super.onAttach(context);
    }

    @Override
    public void onValidationSucceeded() {
        listener.onAddConfirm(exercise,Integer.parseInt(setEditText.getText().toString()), Integer.parseInt(repEditText.getText().toString()), dayView.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //Add an alert dialog
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

