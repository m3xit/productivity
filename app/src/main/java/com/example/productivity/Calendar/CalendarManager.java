package com.example.productivity.Calendar;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

class CalendarManager {

    private List<Appointment> appointments;
    private Appointment[][] calendar;
    private Appointment[][] defaultCalendar;
    private Calendar date;
    private final String appointmentKey = "com.example.productivity.CalendarActivity.appointmentKey";
    private final String todayKey = "com.example.productivity.CalendarActivity.todayKey";

    CalendarManager() {
        date = GregorianCalendar.getInstance();
        readAppointments();
        createPlan();
        createCalendar();
    }

    private void createCalendar() {
        calendar = defaultCalendar;

        insertAppointments();
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
        Collections.sort(appointments);
        writeAppointments();
        createCalendar();
    }

    void removeAppointment(int index) {
        appointments.remove(index);
        writeAppointments();
        createCalendar();
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
        return date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
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
        defaultCalendar = new Appointment[7][30];

        defaultCalendar[0] = createDay(Calendar.MONDAY);
        defaultCalendar[1] = createDay(Calendar.TUESDAY);
        defaultCalendar[2] = createDay(Calendar.WEDNESDAY);
        defaultCalendar[3] = createDay(Calendar.THURSDAY);
        defaultCalendar[4] = createDay(Calendar.FRIDAY);
        defaultCalendar[5] = createDay(Calendar.SATURDAY);
        defaultCalendar[6] = createDay(Calendar.SUNDAY);
    }

    private void insertAppointments() {
        for (Appointment a : appointments) {
            int day = inWeek(a);
            if (day >= 0) {
                int index = (a.getCalendar().get(Calendar.HOUR) + (a.getCalendar().get(Calendar.AM_PM) * 12) - 9) * 2 + (a.getCalendar().get(Calendar.MINUTE) / 30);
                if (index < 0) {
                    index = 0;
                } else if (index >= calendar[day].length) {
                    index = calendar[day].length - 1;
                }

                calendar[day][index] = a;
                System.out.println(a.getName() + "*****************************************************");
            }
        }
    }

    private int inWeek(Appointment appointment) {
        Calendar date = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    break;
                case 1:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    break;
                case 2:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    break;
                case 3:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    break;
                case 4:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    break;
                case 5:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    break;
                case 6:
                    date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    break;
            }

            if (appointment.getCalendar().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) && appointment.getCalendar().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
                return i;
            }
        }

        return -1;
    }

    private void writeAppointments() {
        MainActivity.store.write(appointmentKey, appointments);
    }

    private void readAppointments() {
        appointments = (ArrayList<Appointment>) MainActivity.store.read(appointmentKey);

        if (appointments == null) {
            appointments = new ArrayList<>();
        }

        Collections.sort(appointments);
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
