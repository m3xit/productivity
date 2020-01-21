package com.example.productivity.Calendar;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class CalendarManager {

    private List<Appointment> appointments, calendar;
    private Calendar date;
    private final String appointmentKey = "com.example.productivity.CalendarActivity.appointmentKey";

    CalendarManager() {
        readAppointments();
        createPlan();
        date = GregorianCalendar.getInstance();
    }

    List<Appointment> getAppointments() {
        return appointments;
    }

    void addAppointment(Appointment appointment) {
        addAppointment(0, appointment);
    }

    void addAppointment(int index, Appointment appointment) {
        appointments.add(index, appointment);
        writeAppointments();
    }

    void removeAppointment(int index) {
        appointments.remove(index);
        writeAppointments();
    }

    List<Appointment> getPlan() {
        return calendar;
    }

    private List<Appointment> createPlan() {
        calendar = new ArrayList<>();

        AppointmentCategory[] plan = new AppointmentCategory[] {AppointmentCategory.Cookie, AppointmentCategory.Cookie, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Sport, AppointmentCategory.Sport, AppointmentCategory.Sport, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby};

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 7; j++) {
                calendar.add(new Appointment(plan[i].name(), plan[i]));
            }
        }

        return calendar;
    }

    private void writeAppointments() {
        MainActivity.store.write(appointmentKey, appointments);
    }

    private void readAppointments() {
        appointments = (ArrayList<Appointment>) MainActivity.store.read(appointmentKey);

        if (appointments == null) {
            appointments = new ArrayList<>();
        }
    }

    private void createDay(int dayOfWeek) {
        //todo?
    }
}

enum DAY_OF_WEEK {
    Monday(0),
    Tuesday(1),
    Wednesday(2),
    Thursday(3),
    Friday(4),
    Saturday(5),
    Sunday(6);

    private int number;

    DAY_OF_WEEK(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}