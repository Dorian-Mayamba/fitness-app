package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.adapters;

import android.content.Context;
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

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.database.entities.Exercise;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises;
    LayoutInflater inflater;
    public ExerciseAdapter(Context context, List<Exercise> exercises){
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exercise_items, parent, false);
        return new ExerciseViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = this.exercises.get(position);
        holder.exerciseNameView.setText(exercise.getExerciseName());
        Picasso.get().load(exercise.getExerciseImg()).into(holder.exerciseImageView);
    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    public void updateExercisesList(List<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final ExerciseAdapter adapter;

        private final TextView exerciseNameView;

        private final ImageView exerciseImageView;
        private final Button instructionButton, deleteButton, editButton;
        public ExerciseViewHolder(@NonNull View itemView, ExerciseAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.deleteButton = itemView.findViewById(R.id.delete_button);
            this.editButton = itemView.findViewById(R.id.edit_button);
            this.instructionButton = itemView.findViewById(R.id.instruction_button);
            this.exerciseImageView = itemView.findViewById(R.id.exercise_img);
            this.exerciseNameView = itemView.findViewById(R.id.exercise_name);
        }

        public ExerciseAdapter getAdapter() {
            return adapter;
        }

        public TextView getExerciseNameView() {
            return exerciseNameView;
        }

        public ImageView getExerciseImageView() {
            return exerciseImageView;
        }

        public Button getInstructionButton() {
            return instructionButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

        public Button getEditButton() {
            return editButton;
        }
    }
}