package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.MoreInfoButtonClickedListener;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    List<Exercise> exercises;

    private final LayoutInflater mInflater;

    public ExerciseAdapter(@NonNull Context context, List<Exercise> exercises) {
        mInflater = LayoutInflater.from(context);
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View exerciseItemView = mInflater.inflate(R.layout.gym_items, parent, false);
        return new ExerciseViewHolder(exerciseItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = this.exercises.get(position);
        holder.setExercise(exercise);
        String displayText = holder.getExercise().getName() + " " + holder.getExercise().getTarget() + " "
                + holder.getExercise().getBodyPart();
        holder.exerciseTextView.setText(displayText);
        holder.getButton().setOnClickListener(new MoreInfoButtonClickedListener(holder.getExercise()));
        Log.i(MainActivity.TAG, exercise.getGifUrl());
        Picasso.get().load(holder.exercise.getGifUrl()).into(holder.imageView);
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
        private TextView exerciseTextView;
        private ExerciseAdapter exerciseAdapter;

        private ImageView imageView;

        private Button button;
        public ExerciseViewHolder(@NonNull View itemView, ExerciseAdapter adapter) {
            super(itemView);
            this.exerciseTextView = itemView.findViewById(R.id.exercise);
            this.exerciseAdapter = adapter;
            this.button = itemView.findViewById(R.id.more_info_button);
            this.imageView = itemView.findViewById(R.id.exercise_image);
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

        public TextView getExerciseTextView() {
            return exerciseTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
