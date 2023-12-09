package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {
    private Geometry geometry;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
