package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import java.io.Serializable;

public class Photo implements Serializable {
    private String photo_reference;

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }
}
