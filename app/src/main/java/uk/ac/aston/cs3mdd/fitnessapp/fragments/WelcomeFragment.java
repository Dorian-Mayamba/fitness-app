package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentWelcomeBinding;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    FragmentWelcomeBinding binding;

    private Button homeButton;

    ConstraintLayout constraintLayout;

    public interface OnAppStartListener {
        void onAppStarted();
    }

    OnAppStartListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);
        constraintLayout = binding.container;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeButton = view.findViewById(R.id.home_button);
        constraintLayout.getBackground().setColorFilter(ColorFilterCreator.createColorFilter(0.65f));
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAppStarted();
                NavHostFragment.findNavController(WelcomeFragment.this)
                        .navigate(R.id.action_WelcomeFragment_to_HomeFragment);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            listener = (OnAppStartListener) context;
        }catch (ClassCastException exception){
            throw new ClassCastException("The class "+ context.toString() + " should implement the onAppStartListener interface");
        }
        super.onAttach(context);
    }
}