package com.example.productivity.Calendar;

import com.example.productivity.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment implements Serializable, Comparable<Appointment> {

    private String name;
    private Calendar date;
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

    int getDayOfWeek() {
        return  date.get(Calendar.DAY_OF_WEEK);
    }

    int getDayOfYear() {
        return  date.get(Calendar.DAY_OF_YEAR);
    }

    int getYear() {
        return  date.get(Calendar.YEAR);
    }

    int getHour() {
        return date.get(Calendar.HOUR);
    }

    int getMinute() {
        return date.get(Calendar.MINUTE);
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

    @Override
    public int compareTo(Appointment appointment) {
        return getDate().compareTo(appointment.getDate());
    }

    public Calendar getCalendar() {
        return date;
    }
}

enum AppointmentCategory {
    Sport,
    Work,
    Hobby,
    Essen,
    Other;

    private int color;

    static {
        Sport.color = R.color.calendarSport;
        Work.color = R.color.calendarWork;
        Hobby.color = R.color.calendarHobby;
        Essen.color = R.color.calendarEssen;
        Other.color = R.color.calendarOther;
    }

    public int getColor() {
        return color;
    }
}