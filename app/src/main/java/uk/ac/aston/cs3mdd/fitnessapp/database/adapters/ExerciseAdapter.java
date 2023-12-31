package uk.ac.aston.cs3mdd.fitnessapp.database.adapters;

import android.content.Context;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.database.entities.Exercise;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.DeleteExerciseDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.fragments.ManageWorkoutPlanFragmentDirections;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises;

    private FragmentManager manager;
    LayoutInflater inflater;

    public ExerciseAdapter(Context context, List<Exercise> exercises, FragmentManager manager) {
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exercise_items, parent, false);
        return new ExerciseViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = this.exercises.get(position);
        holder.exerciseNameView.setText(exercise.getExerciseName());
        holder.bodyPartView.setText(exercise.getBodyPart());
        holder.numSetView.setText(exercise.getNumberOfSets() + " sets");
        holder.numRepView.setText(exercise.getNumberOfReps() + " reps");
        holder.deleteExerciseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteExerciseDialogFragment dialogFragment = new DeleteExerciseDialogFragment(exercise);
                dialogFragment.show(manager, "DELETE_EXERCISE");
            }
        });
        ColorFilter colorFilter = ColorFilterCreator.createColorFilter(0.65f);
        holder.exerciseImageView.setColorFilter(colorFilter);
        Glide.with(holder.itemView)
                .asGif()
                .load(exercise.getExerciseImg())
                .into(holder.exerciseImageView);
        holder.updateExerciseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageWorkoutPlanFragmentDirections.ActionCreateWorkoutPlanToEditExerciseFragment action =
                        ManageWorkoutPlanFragmentDirections.actionCreateWorkoutPlanToEditExerciseFragment(exercise);
                Navigation.findNavController(v)
                        .navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    public void updateExercisesList(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final ExerciseAdapter adapter;

        private final TextView exerciseNameView, bodyPartView, numSetView, numRepView;

        private final ImageView exerciseImageView, deleteExerciseView, updateExerciseView;
        private final Button instructionButton;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseImageView = itemView.findViewById(R.id.exercise_img);
            this.exerciseNameView = itemView.findViewById(R.id.exercise_name);
            this.instructionButton = itemView.findViewById(R.id.more_info_button);
            this.deleteExerciseView = itemView.findViewById(R.id.delete_exercise_img);
            this.updateExerciseView = itemView.findViewById(R.id.update_exercise_img);
            this.bodyPartView = itemView.findViewById(R.id.body_part_text);
            this.numSetView = itemView.findViewById(R.id.exercise_num_set_text);
            this.numRepView = itemView.findViewById(R.id.exercise_num_rep_text);
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
    }
}
