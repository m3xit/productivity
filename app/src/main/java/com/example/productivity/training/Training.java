package com.example.productivity.training;

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
    private static final long serialVersionUID = 526042654751858903L;

    public Training(String name, TrainingType type) {
        this.name = name.replace("\n", " ").trim();
        this.type = type;
        this.date = new Date();
        this.duration = 100;
        exercises = new ArrayList<>();
    }

    void addSet(int position, String set) {
        if (!set.equals("")) {
            exercises.get(position).addSet(set);
        }
    }

    int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace("\n", " ").trim();
    }

    TrainingType getType() {
        return type;
    }

    String getDate() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        return ft.format(date);
    }

    List<Exercise> getExercises() {
        return exercises;
    }

    Exercise getExercise(int position) {
        if (position < exercises.size()) {
            return exercises.get(position);
        }

        return null;
    }

    void addExercise(String name) {
        name = name.trim();
        if (!name.equals("")) {
            exercises.add(new Exercise(name, ""));
        }
    }

    void removeExercise(int position) {
        exercises.remove(position);
    }
}

enum TrainingType {
    CARDIO,
    GYM,
    CLIMBING,
    OTHER
}