package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import java.io.Serializable;
import java.util.List;

public class OpeningHour implements Serializable {
    private boolean open_now;

    public List<String> getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(List<String> weekday_text) {
        this.weekday_text = weekday_text;
    }

    private List<String> weekday_text;

    public boolean isOpen_now(){
        return this.open_now;
    }
}
