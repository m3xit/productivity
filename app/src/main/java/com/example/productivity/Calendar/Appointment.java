package com.example.productivity.Calendar;

import android.graphics.Color;

import com.example.productivity.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Appointment implements Serializable {

    private String name;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final long serialVersionUID = -3825880683075034385L;
    private AppointmentCategory category;

    Appointment(String name) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        this.name = name;
        this.mYear = cal.get(Calendar.YEAR);
        this.mMonth = cal.get(Calendar.MONTH);
        this.mDay = cal.get(Calendar.DAY_OF_MONTH);
        this.mHour = cal.getTime().getHours();
        this.mMinute = cal.getTime().getMinutes();
        this.category = AppointmentCategory.Other;
    }

    Appointment(String name, int mYear, int mMonth, int mDay, int mHour, int mMinute, AppointmentCategory category) {
        this.name = name;
        this.mMinute = mMinute;
        this.mHour = mHour;
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.category = category;
    }

    public AppointmentCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    String getDate() {
        return String.format("%02d.%02d.%02d %02d:%02d", mDay, mMonth + 1, mYear, mHour, mMinute);
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