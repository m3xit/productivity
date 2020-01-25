package com.example.productivity.Training;

import java.io.Serializable;

public class Set implements Serializable {
    private int weight, reps;
    private static final long serialVersionUID = 6117840541671362123L;

    public Set(int weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
