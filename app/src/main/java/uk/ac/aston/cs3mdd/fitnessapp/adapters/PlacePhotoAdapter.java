package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlacePhotoAdapter extends RecyclerView.Adapter<PlacePhotoAdapter.PlacePhotoViewHolder>{
    @NonNull
    @Override
    public PlacePhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacePhotoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class PlacePhotoViewHolder extends RecyclerView.ViewHolder{


        public PlacePhotoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
