package uk.ac.aston.cs3mdd.fitnessapp.exercises.adapters;

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
import uk.ac.aston.cs3mdd.fitnessapp.exercises.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners.ExerciseItemClickListener;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.AddExerciseViewHolder>{


    LayoutInflater inflater;

    List<Exercise> exercises;

    private ExerciseItemClickListener exerciseItemClickListener;
    public AddExerciseAdapter(List<Exercise> exercises, Context context, ExerciseItemClickListener listener){
        this.exercises = exercises;
        inflater = LayoutInflater.from(context);
        this.exerciseItemClickListener = listener;
    }

    @NonNull
    @Override
    public AddExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.add_exercise_dialog_items, parent,false);
        return new AddExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseViewHolder holder, int position) {
        Exercise exercise = this.exercises.get(position);
        holder.exerciseText.setText(exercise.getName());
        Picasso.get().load(exercise.getGifUrl()).into(holder.exerciseImage);
        holder.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseItemClickListener.onAddExerciseClick(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class AddExerciseViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseText;

        private ImageView exerciseImage;

        private Button addExerciseButton;
        public AddExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exerciseText = itemView.findViewById(R.id.add_exercise_name_text);
            this.exerciseImage = itemView.findViewById(R.id.dialog_add_exercise_image_text_view);
            this.addExerciseButton = itemView.findViewById(R.id.add_exercise_button);
        }
    }
}
