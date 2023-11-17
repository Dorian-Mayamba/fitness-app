package uk.ac.aston.cs3mdd.fitnessapp.exercises;

import java.io.Serializable;
import java.util.Arrays;

public class Exercise implements Serializable {
    private int id;
    private String name;
    private String description;

    private int exercise_base;

    private int language;

    private int category;

    private int [] muscles;

    private int [] muscles_secondary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_base() {
        return exercise_base;
    }

    public void setExercise_base(int exercise_base) {
        this.exercise_base = exercise_base;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int[] getMuscles() {
        return muscles;
    }

    public void setMuscles(int[] muscles) {
        this.muscles = muscles;
    }

    public int[] getMuscles_secondary() {
        return muscles_secondary;
    }

    public void setMuscles_secondary(int[] muscles_secondary) {
        this.muscles_secondary = muscles_secondary;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exercise_base=" + exercise_base +
                ", language=" + language +
                ", category=" + category +
                ", muscles=" + Arrays.toString(muscles) +
                ", muscles_secondary=" + Arrays.toString(muscles_secondary) +
                '}';
    }
}
