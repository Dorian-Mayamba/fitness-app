package uk.ac.aston.cs3mdd.fitnessapp.collections;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;

public class PlaceList {
    private List<Place> results;

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
