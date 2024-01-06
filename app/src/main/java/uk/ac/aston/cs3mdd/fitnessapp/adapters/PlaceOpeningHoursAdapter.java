package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;

public class PlaceOpeningHoursAdapter extends RecyclerView.Adapter<PlaceOpeningHoursAdapter.PlaceOpeningHoursViewHolder> {

    private LayoutInflater inflater;

    private List<String> openingHours;

    public PlaceOpeningHoursAdapter(List<String>  openingHours){
        this.openingHours = openingHours;
    }
    @NonNull
    @Override
    public PlaceOpeningHoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.opening_hours_item, parent,false);
        return new PlaceOpeningHoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOpeningHoursViewHolder holder, int position) {
        String openingHour = openingHours.get(position);
        String [] splitDayTime = openingHour.split(" ");
        String openDay = splitDayTime[0];
        String openTime = splitDayTime[1];
        holder.openingDayText.setText(openDay);
        holder.openingHourText.setText(openTime);
    }

    public void updateOpeningHours(List<String> openingHours){
        this.openingHours = openingHours;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return openingHours.size();
    }

    static class PlaceOpeningHoursViewHolder extends RecyclerView.ViewHolder{
        TextView openingDayText;

        TextView openingHourText;
        public PlaceOpeningHoursViewHolder(@NonNull View itemView) {
            super(itemView);
            openingDayText = itemView.findViewById(R.id.day_text);
            openingHourText = itemView.findViewById(R.id.time_text);
        }
    }
}
