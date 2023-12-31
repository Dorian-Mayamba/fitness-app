package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.content.Context;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.AddExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.fragments.GymFragmentDirections;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    List<Exercise> exercises;

    private final LayoutInflater mInflater;

    private MainActivity activity;

    public ExerciseAdapter(@NonNull Context context, List<Exercise> exercises) {
        mInflater = LayoutInflater.from(context);
        this.exercises = exercises;
        activity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View exerciseItemView = mInflater.inflate(R.layout.gym_items, parent, false);
        return new ExerciseViewHolder(exerciseItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.exerciseNameView.setText(exercise.getName());
        holder.bodyPartView.setText(exercise.getBodyPart());

        holder.addSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GymFragmentDirections.ActionGymFragmentToAddExerciseFragment action =
                        GymFragmentDirections.actionGymFragmentToAddExerciseFragment(exercise);
                Navigation.findNavController(v)
                        .navigate(action);
            }
        });
        ColorFilter colorFilter = ColorFilterCreator.createColorFilter(0.65f);
        holder.imageView.setColorFilter(colorFilter);
        Glide.with(holder.itemView)
                .asGif()
                .load(exercise.getGifUrl())
                .into(holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GymFragmentDirections.ActionGymFragmentToExerciseDescriptionFragment action
                        = GymFragmentDirections.actionGymFragmentToExerciseDescriptionFragment(exercise);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    public void updateList(List<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder{
        private Exercise exercise;
        private TextView exerciseNameView;

        private TextView bodyPartView;
        private ExerciseAdapter exerciseAdapter;

        private ImageView imageView;

        private TextView addSign;
        private Button button;
        public ExerciseViewHolder(@NonNull View itemView, ExerciseAdapter adapter) {
            super(itemView);
            this.exerciseNameView = itemView.findViewById(R.id.exercise_name_view);
            this.exerciseAdapter = adapter;
            this.button = itemView.findViewById(R.id.more_info_button);
            this.imageView = itemView.findViewById(R.id.exercise_image);
            this.bodyPartView = itemView.findViewById(R.id.body_part_text_view);
            this.addSign = itemView.findViewById(R.id.add_sign);
        }

        public void setExercise(Exercise exercise){
            this.exercise = exercise;
        }

        public Exercise getExercise() {
            return exercise;
        }

        public Button getButton() {
            return button;
        }

        public TextView getExerciseNameView() {
            return exerciseNameView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
