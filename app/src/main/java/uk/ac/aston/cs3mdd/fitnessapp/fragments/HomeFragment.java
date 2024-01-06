package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.WorkoutImageAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentHomeBinding;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.WorkoutImageDetail;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ImageView exerciseImage;
    private ImageView workoutPlanImage;
    private SliderView sliderView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseImage = view.findViewById(R.id.image_exercise);
        workoutPlanImage = view.findViewById(R.id.image_workout_plan);
        ColorFilter colorFilter = ColorFilterCreator.createColorFilter(0.75f);
        sliderView = view.findViewById(R.id.slider_view);
        List<WorkoutImageDetail> details = new ArrayList<>();
        details.addAll(
                Arrays.asList(new WorkoutImageDetail[]{
                        new WorkoutImageDetail("Abs", String.valueOf(5), String.valueOf(10), Uri.parse("android.resource://uk.ac.aston.cs3mdd.fitnessapp/" + R.drawable.ic_abs_workout)),
                        new WorkoutImageDetail("pull ups", String.valueOf(5), String.valueOf(10), Uri.parse("android.resource://uk.ac.aston.cs3mdd.fitnessapp/" + R.drawable.ic_pull_up)),
                        new WorkoutImageDetail("Chest press", String.valueOf(5), String.valueOf(10), Uri.parse("android.resource://uk.ac.aston.cs3mdd.fitnessapp/" + R.drawable.ic_chest_3a)),
                        new WorkoutImageDetail("Dumbbell curl", String.valueOf(5), String.valueOf(10), Uri.parse("android.resource://uk.ac.aston.cs3mdd.fitnessapp/" + R.drawable.dumbbell_curl))
                })
        );
        WorkoutImageAdapter adapter = new WorkoutImageAdapter(details);
        sliderView.setSliderAdapter(adapter);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        exerciseImage.setColorFilter(colorFilter);
        workoutPlanImage.setColorFilter(colorFilter);
    }
}