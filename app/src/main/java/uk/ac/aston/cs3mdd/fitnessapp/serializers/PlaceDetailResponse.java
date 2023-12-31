package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import java.io.Serializable;

public class PlaceDetailResponse implements Serializable {
    private PlaceDetail result;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PlaceDetail getResult() {
        return result;
    }

    public void setResult(PlaceDetail result) {
        this.result = result;
    }


}
