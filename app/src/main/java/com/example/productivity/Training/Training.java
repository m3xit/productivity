package com.example.productivity.Training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import java.util.List;

public class Training implements Serializable {
    private String name;
    private TrainingType type;
    private Date date;
    private int duration;
    private List<Exercise> exercises;

    public Training(String name, TrainingType type) {
        this.name = name;
        this.type = type;
        this.date = new Date();
        this.duration = 100;
        exercises = new ArrayList<>();
    }

    void addExercise(Exercise e) {
        exercises.add(e);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    String getName() {
        return name;
    }

    TrainingType getType() {
        return type;
    }

    String getDate() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        return ft.format(date);
    }

    int getDuration() {
        return duration;
    }

    List<Exercise> getExercises() {
        return exercises;
    }
}

enum TrainingType {
    CARDIO,
    GYM,
    CLIMBING,
    OTHER
}