package com.example.productivity;

import java.time.LocalDate;
import java.util.Date;
import java.text.*;

public class Training {
    private String name;
    private TrainingType type;
    private Date date;
    private int duration;

    public Training(String name, TrainingType type) {
        this.name = name;
        this.type = type;
        this.date = new Date();
        this.duration = 100;
    }

    public String getName() {
        return name;
    }

    public TrainingType getType() {
        return type;
    }

    public String getDate() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        return ft.format(date);
    }

    public int getDuration() {
        return duration;
    }
}

enum TrainingType {
    CARDIO,
    GYM
}