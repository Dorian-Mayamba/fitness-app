package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaceDetail implements Serializable {
    private String name;
    private String formatted_address;
    private String formatted_phone_number;
    private List<Photo> photos;
    private List<Bitmap> photoGraphics = new ArrayList<>();
    public List<Bitmap> getPhotoGraphics() {
        return photoGraphics;
    }
    public void addPhotoGraphic(Bitmap photoGraphic){
        this.photoGraphics.add(photoGraphic);
    }
    public List<Photo> getPhotos() {
        return photos;
    }
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    public OpeningHour getOpening_hours() {
        return opening_hours;
    }
    public void setOpening_hours(OpeningHour opening_hours) {
        this.opening_hours = opening_hours;
    }
    private OpeningHour opening_hours;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormatted_address() {
        return formatted_address;
    }
    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }
    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }
}
