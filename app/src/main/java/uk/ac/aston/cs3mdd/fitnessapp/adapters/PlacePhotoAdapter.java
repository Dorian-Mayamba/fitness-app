package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;

public class PlacePhotoAdapter extends SliderViewAdapter<PlacePhotoAdapter.PlacePhotosViewHolder> {

    private LayoutInflater inflater;

    private List<Bitmap> placePhotos;

    public PlacePhotoAdapter(List<Bitmap> placePhotos){
        this.placePhotos = placePhotos;
    }
    @Override
    public PlacePhotosViewHolder onCreateViewHolder(ViewGroup parent) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_photo_items, parent,false);
        return new PlacePhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlacePhotosViewHolder viewHolder, int position) {
        Bitmap placePhoto = placePhotos.get(position);
        viewHolder.placePhoto.setImageBitmap(placePhoto);
    }

    public void submitList(List<Bitmap> newPlacePhotos){
        this.placePhotos = newPlacePhotos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return placePhotos.size();
    }

    static class PlacePhotosViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView placePhoto;
        public PlacePhotosViewHolder(View itemView) {
            super(itemView);
            placePhoto = itemView.findViewById(R.id.place_photo);
        }
    }
}
