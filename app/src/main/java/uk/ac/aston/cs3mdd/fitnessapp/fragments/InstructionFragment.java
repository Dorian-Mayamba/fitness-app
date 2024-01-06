package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.util.Arrays;

import uk.ac.aston.cs3mdd.fitnessapp.adapters.InstructionAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentInstructionBinding;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionFragment extends Fragment implements View.OnClickListener{

    private FragmentInstructionBinding biding;

    private Exercise exercise;
    private SliderView instructionSlider;

    private TextView exerciseName;

    private ImageView exerciseImage;

    private ImageView backImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exercise = InstructionFragmentArgs.fromBundle(getArguments()).getExercise();
        biding = FragmentInstructionBinding.inflate(inflater,container,false);
        return biding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseImage = view.findViewById(R.id.exercise_image);
        exerciseName = view.findViewById(R.id.exercise_name);
        instructionSlider = view.findViewById(R.id.slider_view);
        instructionSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        instructionSlider.setScrollTimeInSec(3);
        instructionSlider.setAutoCycle(true);
        instructionSlider.startAutoCycle();
        InstructionAdapter adapter = new InstructionAdapter(Arrays.asList(exercise.getExerciseInstructions().split("\n")));
        instructionSlider.setSliderAdapter(adapter);
        exerciseName.setText(String.format("%s's instructions", exercise.getExerciseName()));
        exerciseImage.setColorFilter(ColorFilterCreator.createColorFilter(0.65f));
        Glide.with(view)
                .asGif()
                .load(exercise.getExerciseImg())
                .into(exerciseImage);
        backImageView = biding.backBtnImg;
        backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_InstructionFragment_to_create_workout_plan);
    }
}