package uk.ac.aston.cs3mdd.fitnessapp.serializers;

import android.net.Uri;

public class WorkoutImageDetail {
    private String exerciseName;

    private String numberOfSets;

    private String numberOfReps;

    private Uri imageUri;

    public WorkoutImageDetail(String exerciseName, String numberOfSets, String numberOfReps, Uri imageUri) {
        this.exerciseName = exerciseName;
        this.numberOfSets = numberOfSets;
        this.numberOfReps = numberOfReps;
        this.imageUri = imageUri;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(String numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public String getNumberOfReps() {
        return numberOfReps;
    }

    public void setNumberOfReps(String numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
