package com.example.productivity.Training;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String name, sets;

    public Exercise(String name, String sets) {
        this.sets = sets;
        this.name = name;
    }

    public String getSets() {
        return sets;
    }

    public String getName() {
        return name;
    }

    public void addSet(String set) {
        if (sets.equals("")) {
            sets = set;
        } else {
            this.sets += "\n" + set;
        }
    }
}
