package com.example.productivity.training;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String name, sets;
    private static final long serialVersionUID = 6117840541671362280L;

    Exercise(String name, String sets) {
        this.sets = sets;
        this.name = name.replace("\n", " ").trim();
    }

    String getSets() {
        return sets;
    }

    public String getName() {
        return name;
    }

    void addSet(String set) {
        if (sets.equals("")) {
            sets = set.trim();
        } else {
            this.sets += "\n" + set.trim();
        }
    }
}
