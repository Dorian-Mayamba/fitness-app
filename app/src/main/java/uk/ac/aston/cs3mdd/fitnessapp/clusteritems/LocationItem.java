package uk.ac.aston.cs3mdd.fitnessapp.clusteritems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class LocationItem implements ClusterItem {

    private LatLng position;

    private String title;
    private String snippet;
    public LocationItem(LatLng position, String title, String snippet){
        this.title = title;
        this.position = position;
        this.snippet = snippet;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    @Nullable
    @Override
    public Float getZIndex() {
        return 0.2f;
    }
}
