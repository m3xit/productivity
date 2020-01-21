package com.example.productivity.Calendar;

import com.example.productivity.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment implements Serializable {

    private String name;
    private Calendar date;
    //private int mYear, mMonth, mDay, mHour, mMinute;
    private static final long serialVersionUID = -3825880683075034385L;
    private AppointmentCategory category;

    Appointment(String name) {
        this.name = name;
        this.category = AppointmentCategory.Other;
        date = GregorianCalendar.getInstance();
    }

    Appointment(String name, int year, int month, int day, int hour, int minute, AppointmentCategory category) {
        this.name = name;
        this.category = category;
        date = new GregorianCalendar(year, month, day, hour, minute);

    }

    public AppointmentCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    String getDate() {
        return DateFormat.getDateInstance().format(date.getTime());
    }
}

enum AppointmentCategory {
    Sport,
    Work,
    Masterarbeit,
    Hobby,
    Other,
    Essen,
    Cookie;

    private int color;

    static {
        Sport.color = R.color.darkBlue;
        Work.color = R.color.darkBlue;
        Masterarbeit.color = R.color.darkBlue;
        Hobby.color = R.color.darkBlue;
        Other.color = R.color.darkBlue;
        Essen.color = R.color.darkBlue;
        Cookie.color = R.color.darkBlue;
    }

    public int getColor() {
        return color;
    }
}