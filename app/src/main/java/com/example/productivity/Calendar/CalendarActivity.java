package com.example.productivity.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.productivity.MainActivity;
import com.example.productivity.Notes.NotesAdapter;
import com.example.productivity.R;
import com.example.productivity.stuff.GridSpacingItemDecoration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements AppointmentAdapter.ItemClickListener, CalendarAdapter.ItemClickListener {

    private List<Appointment> appointments;
    private List<Appointment> calendar;
    private RecyclerView recyclerView, calendarView;
    private AppointmentAdapter adapter;
    private CalendarAdapter calendarAdapter;
    private int requestCodeNewAppointment = 1337;

    public static String AppointmentCreateExtra = "com.example.productivity.WeekPlanActivity.AppointmentCreateExtra";

    private final String appointmentKey = "com.example.productivity.CalendarActivity.appointmentKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.calendar);
        setContentView(R.layout.activity_calendar);

        createCalendar();

        readAppointments();

        initializeAdapters();
    }

    protected void onDestroy() {
        super.onDestroy();

        writeAppointments();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.appointment) {
            appointments.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, appointments.size());
        }
    }

    private void createCalendar() {
        calendar = new ArrayList<>();
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

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, NewAppointmentActivity.class);
        startActivityForResult(intent, requestCodeNewAppointment);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeNewAppointment) {
            if (resultCode == RESULT_OK) {
                Appointment newAppointment = (Appointment) data.getExtras().get(AppointmentCreateExtra);
                appointments.add(0, newAppointment);
                adapter.notifyDataSetChanged();
                writeAppointments();
            }
        }
    }

    private void initializeAdapters() {
        recyclerView = findViewById(R.id.appointment_list);
        recyclerView.setHasFixedSize(true);

        adapter = new AppointmentAdapter(appointments);
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.calendar);
        calendarView.setHasFixedSize(true);

        calendarAdapter = new CalendarAdapter(calendar);
        calendarAdapter.setClickListener(this);
        calendarView.setLayoutManager(new GridLayoutManager(this, 7));
        calendarView.addItemDecoration(new GridSpacingItemDecoration(7, 20, false));
        calendarView.setAdapter(calendarAdapter);
    }

    private void readAppointments() {
        appointments = (ArrayList<Appointment>) MainActivity.store.read(appointmentKey);

        if (appointments == null) {
            appointments = new ArrayList<>();
        }
    }

    private void writeAppointments() {
        MainActivity.store.write(appointmentKey, appointments);
    }
}


