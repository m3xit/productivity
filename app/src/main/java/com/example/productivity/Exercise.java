package com.example.productivity;

public class Exercise {

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
}
