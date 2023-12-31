package uk.ac.aston.cs3mdd.fitnessapp.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"day"}, unique = true)})
public class WorkoutPlan {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "day")
    private String day;
}
