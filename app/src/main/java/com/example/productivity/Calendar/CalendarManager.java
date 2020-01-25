package com.example.productivity.Calendar;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

class CalendarManager {

    private List<Appointment> appointments;
    private Appointment[][] calendar;
    private Calendar date;
    private final String appointmentKey = "com.example.productivity.CalendarActivity.appointmentKey";
    private final String todayKey = "com.example.productivity.CalendarActivity.todayKey";

    CalendarManager() {
        date = GregorianCalendar.getInstance();
        readAppointments();
        createPlan();
    }

    List<Appointment> getAppointments() {
        return appointments;
    }

    int getAppointmentsTodayCount() {
        int count = 0;
        for (Appointment a : appointments) {
            if (isToday(a)) {
                System.out.println(a.getName() + "*******************************" + a.getDate());
                count++;
            }
        }

        return count;
    }

    private boolean isToday(Appointment appointment) {
        return date.get(Calendar.DAY_OF_YEAR) == appointment.getDayOfYear() && date.get(Calendar.YEAR) == appointment.getYear();
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

    boolean firstTimeToday() {
        Calendar today = (Calendar) MainActivity.store.read(todayKey);

        if (today == null) {
            today = date;
        }

        boolean first = !(today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.YEAR) == date.get(Calendar.YEAR));
        MainActivity.store.write(todayKey, date);
        return first;
    }

    String getDayOfWeek() {
        return date.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.US);
    }

    boolean sameDayOfWeek(Appointment appointment) {
        return date.get(Calendar.DAY_OF_WEEK) == appointment.getDayOfWeek();
    }

    boolean isNow(int position) {
        int current = (date.get(Calendar.HOUR) + (date.get(Calendar.AM_PM) * 12) - 9) * 2 + (date.get(Calendar.MINUTE) / 30);
        return position / calendar.length == current;
    }

    Appointment[][] getPlan() {
        return calendar;
    }

    private void createPlan() {
        calendar = new Appointment[7][30];

        calendar[0] = createDay(Calendar.MONDAY);
        calendar[1] = createDay(Calendar.TUESDAY);
        calendar[2] = createDay(Calendar.WEDNESDAY);
        calendar[3] = createDay(Calendar.THURSDAY);
        calendar[4] = createDay(Calendar.FRIDAY);
        calendar[5] = createDay(Calendar.SATURDAY);
        calendar[6] = createDay(Calendar.SUNDAY);
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

    private Appointment[] createDay(int dayOfWeek) {

        Calendar cal = Calendar.getInstance();

        Appointment[] day = new Appointment[30];

        switch (dayOfWeek) {
            case Calendar.MONDAY://DOW=2
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case Calendar.TUESDAY://DOW=3
                cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case Calendar.WEDNESDAY://DOW=4
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case Calendar.THURSDAY://DOW=5
                cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case Calendar.FRIDAY://DOW=6
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case Calendar.SATURDAY://DOW=7
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            case Calendar.SUNDAY://DOW=1
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
        }

        switch (dayOfWeek) {
            case Calendar.MONDAY://DOW=2
            case Calendar.TUESDAY://DOW=3
            case Calendar.WEDNESDAY://DOW=4
            case Calendar.THURSDAY://DOW=5
                day[0] = new Appointment("Cookie", cal, AppointmentCategory.Hobby);
                day[1] = new Appointment("Cookie", cal, AppointmentCategory.Hobby);
                day[2] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[3] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[4] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[5] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[6] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[7] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[8] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[9] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[10] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[11] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[12] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[13] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[14] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[15] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[16] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[17] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[18] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[19] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[20] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[21] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[22] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[23] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[24] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[25] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[26] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[27] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[28] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[29] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);

                break;
            case Calendar.FRIDAY://DOW=6
                day[0] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[1] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[2] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[3] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[4] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[5] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[6] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[7] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[8] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[9] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[10] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[11] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[12] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[13] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[14] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[15] = new Appointment("Arbeiten", cal, AppointmentCategory.Work);
                day[16] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[17] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[18] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[19] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[20] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[21] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[22] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[23] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[24] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[25] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[26] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[27] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[28] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[29] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);

                break;
            case Calendar.SATURDAY://DOW=7
            case Calendar.SUNDAY://DOW=1
                day[0] = new Appointment("Cookie", cal, AppointmentCategory.Hobby);
                day[1] = new Appointment("Cookie", cal, AppointmentCategory.Hobby);
                day[2] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[3] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[4] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[5] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[6] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[7] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[8] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[9] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[10] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[11] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[12] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[13] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[14] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[15] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[16] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[17] = new Appointment("Masterarbeit", cal, AppointmentCategory.Work);
                day[18] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[19] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[20] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[21] = new Appointment("Sport", cal, AppointmentCategory.Sport);
                day[22] = new Appointment("Essen", cal, AppointmentCategory.Essen);
                day[23] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[24] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[25] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[26] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[27] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[28] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);
                day[29] = new Appointment("Hobby", cal, AppointmentCategory.Hobby);

                break;

        }

        return day;
    }
}
