package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.WorkoutImageDetail;
import uk.ac.aston.cs3mdd.fitnessapp.util.ColorFilterCreator;

public class WorkoutImageAdapter extends SliderViewAdapter<WorkoutImageAdapter.WorkoutImageViewHolder> {

    private List<WorkoutImageDetail> imageDetails;

    private LayoutInflater inflater;
    public WorkoutImageAdapter(List<WorkoutImageDetail> imageDetails){
        this.imageDetails = imageDetails;
    }

    @Override
    public WorkoutImageViewHolder onCreateViewHolder(ViewGroup parent) {
        inflater = LayoutInflater.from(parent.getContext());
        return new WorkoutImageViewHolder(inflater.inflate(R.layout.home_items, parent, false));
    }
    @Override
    public void onBindViewHolder(WorkoutImageViewHolder viewHolder, int position) {
        float brightness = 0.55f;
        WorkoutImageDetail imageDetail = this.imageDetails.get(position);
        viewHolder.numberOfRepsView.setText("reps: " + imageDetail.getNumberOfReps());
        viewHolder.exerciseNameView.setText(imageDetail.getExerciseName());
        viewHolder.numberOfSetsView.setText("sets: "+ imageDetail.getNumberOfSets());
        ColorFilter colorFilter = ColorFilterCreator.createColorFilter(brightness);
        viewHolder.sliderImageView.setColorFilter(colorFilter);
        Glide.with(viewHolder.itemView)
                .load(imageDetail.getImageUri())
                .fitCenter()
                .into(viewHolder.sliderImageView);
    }

    @Override
    public int getCount() {
        return this.imageDetails.size();
    }

    static class WorkoutImageViewHolder extends SliderViewAdapter.ViewHolder{

        private ImageView sliderImageView;
        private TextView exerciseNameView;
        private TextView numberOfSetsView;

        private TextView numberOfRepsView;
        public WorkoutImageViewHolder(View itemView) {
            super(itemView);
            this.sliderImageView = itemView.findViewById(R.id.slider_image_view);
            this.exerciseNameView = itemView.findViewById(R.id.workout_name);
            this.numberOfSetsView = itemView.findViewById(R.id.exercise_num_set_text);
            this.numberOfRepsView = itemView.findViewById(R.id.exercise_num_rep_text);
        }

    }
}
