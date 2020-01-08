package com.example.productivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return "" + mDay + "." + mMonth + "." + mYear + " " + mHour + ":" + mMinute;
    }
}
