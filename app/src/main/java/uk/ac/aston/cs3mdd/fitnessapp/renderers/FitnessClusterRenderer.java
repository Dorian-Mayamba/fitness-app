package uk.ac.aston.cs3mdd.fitnessapp.renderers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.Set;

import uk.ac.aston.cs3mdd.fitnessapp.clusteritems.LocationItem;

public class FitnessClusterRenderer extends DefaultClusterRenderer<LocationItem> {

    private final LatLng ASTON_MARKER = new LatLng(52.48679922085286, -1.8878185869387778);

    private final LatLng CURRENT_USER_MARKER = new LatLng(52.486944165236686, -1.9495126024884437);
    public FitnessClusterRenderer(Context context, GoogleMap map, ClusterManager<LocationItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull LocationItem item, @NonNull MarkerOptions markerOptions) {
        if (isAstonMarker(item.getPosition().latitude, item.getPosition().longitude)
            || isUserMarker(item.getPosition().latitude, item.getPosition().longitude)){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }else {
            super.onBeforeClusterItemRendered(item,markerOptions);
        }
    }

    private boolean isAstonMarker(double latitude, double longitude){
        return latitude == ASTON_MARKER.latitude
                && longitude == ASTON_MARKER.longitude;
    }

    private boolean isUserMarker(double latitude, double longitude){
        return latitude == CURRENT_USER_MARKER.latitude
                && longitude == CURRENT_USER_MARKER.longitude;
    }

}
