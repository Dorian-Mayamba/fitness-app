package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Location implements Serializable {
    private double lat;

    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @NonNull
    @Override
    public String toString() {
        return lat + " " + lng;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Location other = (Location) obj;
        return this.lng == other.lng
                && this.lat == other.lng;
    }

    public boolean isValidLocation(double lat, double lng){
        return this.lng == lng
                && this.lat == lat;
    }
}
