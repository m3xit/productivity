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

    Appointment(String name, AppointmentCategory category) {
        this.name = name;
        this.category = category;
        date = GregorianCalendar.getInstance();
    }

    Appointment(String name, Calendar date, AppointmentCategory category) {
        this.name = name;
        this.category = category;
        this.date = date;

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
        Work.color = R.color.darkBackground;
        Masterarbeit.color = R.color.darkRed;
        Hobby.color = R.color.darkGreen;
        Other.color = R.color.lighterBackground;
        Essen.color = R.color.darkYellow;
        Cookie.color = R.color.darkGreen;
    }

    public int getColor() {
        return color;
    }
}