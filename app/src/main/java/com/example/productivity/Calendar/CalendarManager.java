package com.example.productivity.Calendar;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {

    private List<Appointment> appointments;
    private final String appointmentKey = "com.example.productivity.CalendarActivity.appointmentKey";

    public CalendarManager() {
        readAppointments();
    }

    public List<Appointment> getAppointments() {
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

    public List<Appointment> createPlan() {
        List<Appointment> calendar = new ArrayList<>();
        calendar.add(new Appointment("Mo"));
        calendar.add(new Appointment("Di"));
        calendar.add(new Appointment("Mi"));
        calendar.add(new Appointment("Do"));
        calendar.add(new Appointment("Fr"));
        calendar.add(new Appointment("Sa"));
        calendar.add(new Appointment("So"));

        for (int i = 0; i < 30; i++) {
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
            calendar.add(new Appointment(String.valueOf(i)));
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
}
