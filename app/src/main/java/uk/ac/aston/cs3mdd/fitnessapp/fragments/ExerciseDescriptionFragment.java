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

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.InstructionAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentExerciseDescriptionBinding;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDescriptionFragment extends Fragment implements View.OnClickListener{

    FragmentExerciseDescriptionBinding binding;

    private Exercise exercise;

    private SliderView instructionSlider;

    private TextView exerciseName;

    private ImageView exerciseImage;

    private ImageView backImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExerciseDescriptionBinding.inflate(getLayoutInflater());
        exercise = ExerciseDescriptionFragmentArgs.fromBundle(getArguments()).getExercise();
        return binding.getRoot();
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
        InstructionAdapter adapter = new InstructionAdapter(exercise.getInstructions());
        instructionSlider.setSliderAdapter(adapter);
        exerciseName.setText(exercise.getName() + "'s instructions");
        exerciseImage.setColorFilter(ColorFilterCreator.createColorFilter(0.65f));
        Glide.with(view)
                .asGif()
                .load(exercise.getGifUrl())
                .into(exerciseImage);
        backImageView = binding.backBtnImg;
        backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView){
            NavHostFragment.findNavController(ExerciseDescriptionFragment.this)
                    .navigate(R.id.action_ExerciseDescriptionFragment_to_GymFragment);
        }
    }
}