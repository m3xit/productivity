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

        AppointmentCategory[] plan = new AppointmentCategory[] {AppointmentCategory.Cookie, AppointmentCategory.Cookie, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Sport, AppointmentCategory.Sport, AppointmentCategory.Sport, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Masterarbeit, AppointmentCategory.Essen, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby, AppointmentCategory.Hobby};

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 7; j++) {
                calendar.add(new Appointment(plan[i].name()));
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
}
