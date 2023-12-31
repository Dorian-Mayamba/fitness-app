package uk.ac.aston.cs3mdd.fitnessapp.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(
        entity = WorkoutPlan.class,
        parentColumns = "day",
        childColumns = "workout_day",
        onDelete = ForeignKey.CASCADE
))
public class Exercise implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "exercise_name")
    private String exerciseName;

    @ColumnInfo(name = "exercise_instructions")
    private String exerciseInstructions;
    @ColumnInfo(name = "body_part")
    private String bodyPart;

    @ColumnInfo(name = "number_of_sets")
    private int numberOfSets;

    @ColumnInfo(name = "number_of_reps")
    private int numberOfReps;

    @ColumnInfo(name = "exercise_id")
    private String exerciseId;

    @ColumnInfo(name = "workout_day")
    private String workoutDay;

    public String getWorkoutDay() {
        return workoutDay;
    }

    public void setWorkoutDay(String workoutDay) {
        this.workoutDay = workoutDay;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public int getNumberOfReps() {
        return numberOfReps;
    }

    public void setNumberOfReps(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    @ColumnInfo(name = "workoutPlanId")
    private int workoutPlanId;

    public int getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(int workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }

    @ColumnInfo(name = "exercise_img")
    private String exerciseImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseInstructions() {
        return exerciseInstructions;
    }

    public void setExerciseInstructions(String exerciseInstructions) {
        this.exerciseInstructions = exerciseInstructions;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getExerciseImg() {
        return exerciseImg;
    }

    public void setExerciseImg(String exerciseImg) {
        this.exerciseImg = exerciseImg;
    }
}
