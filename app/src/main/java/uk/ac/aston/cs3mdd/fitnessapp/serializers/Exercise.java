package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    private String bodyPart;

    private String gifUrl;

    private String id;

    private String name;

    private String target;

    private List<String> instructions;

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Exercise other = (Exercise) obj;
        return this.id == other.getId()
                && this.name.equals(other.getName())
                && this.instructions.equals(other.getInstructions())
                && this.bodyPart.equals(other.getBodyPart());
    }

    @NonNull
    @Override
    public String toString() {
        return
                this.name + " " + this.bodyPart + " " + this.target+ " " + this.instructions;
    }
}
