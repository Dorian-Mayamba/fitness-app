package uk.ac.aston.cs3mdd.fitnessapp.adapters;

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
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.listeners.ExerciseItemClickListener;

public class EditExerciseAdapter extends RecyclerView.Adapter<EditExerciseAdapter.ExerciseViewHolder> {
    private LayoutInflater inflater;

    private List<Exercise> exercises;

    private ExerciseItemClickListener clickListener;
    public EditExerciseAdapter(Context context, List<Exercise> exercises, ExerciseItemClickListener listener){
        this.exercises = exercises;
        inflater = LayoutInflater.from(context);
        this.clickListener = listener;
    }


    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.edit_dialog_exercise_items, parent,false);
        return new ExerciseViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = this.exercises.get(position);
        holder.exerciseSingleChoiceItem.setText(exercise.getName());
        holder.selectExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onEditExerciseClick(exercise);
            }
        });
        Picasso.get().load(exercise.getGifUrl()).into(holder.exerciseImageView);
    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void submitList(List<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseSingleChoiceItem;

        private ImageView exerciseImageView;
        private EditExerciseAdapter adapter;

        private Button selectExerciseButton;
        public ExerciseViewHolder(@NonNull View itemView, EditExerciseAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseImageView = itemView.findViewById(R.id.dialog_exercise_image_text_view);
            this.exerciseSingleChoiceItem = itemView.findViewById(R.id.exercise_name_text);
            this.selectExerciseButton = itemView.findViewById(R.id.select_exercise_button);
        }
    }
}
