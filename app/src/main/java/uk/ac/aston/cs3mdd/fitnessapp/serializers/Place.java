package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import java.io.Serializable;

public class Place implements Serializable {
    private Geometry geometry;

    private String name;

    private String place_id;

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

    public String getPlace_id() {
        return place_id;
    }
    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }


    public static enum Field{

        FORMATTED_PHONE_NUMBER("formatted_phone_number"),
        FORMATTED_ADDRESS("formatted_address"),
        OPENING_HOURS("opening_hours"),

        PHOTOS("photos"),

        NAME("name");


        private String fieldName;
        Field(String fieldName){
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }
}

