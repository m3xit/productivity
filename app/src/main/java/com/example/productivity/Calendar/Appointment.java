package com.example.productivity.Calendar;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String name;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public Appointment(String name, int mYear, int mMonth, int mDay, int mHour, int mMinute) {
        this.name = name;
        this.mMinute = mMinute;
        this.mHour = mHour;
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return String.format("%02d.%02d.%02d %02d:%02d", mDay, mMonth+1, mYear, mHour, mMinute);
    }
}
