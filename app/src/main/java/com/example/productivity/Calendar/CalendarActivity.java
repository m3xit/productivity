package com.example.productivity.Calendar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;
import com.example.productivity.stuff.GridSpacingItemDecoration;

public class CalendarActivity extends AppCompatActivity implements AppointmentAdapter.ItemClickListener, CalendarAdapter.ItemClickListener {

    private RecyclerView recyclerView, calendarView;
    private AppointmentAdapter adapter;
    private CalendarAdapter calendarAdapter;

    static CalendarManager calendarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.calendar);
        setContentView(R.layout.activity_calendar);

        calendarManager = new CalendarManager();

        initializeAdapters();

        goodMorningGreeting();
    }

    private void goodMorningGreeting() {
        if (calendarManager.firstTimeToday()) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.fragment_calendar_greeting, null);

            final TextView greetingText = dialogView.findViewById(R.id.greetingText);
            greetingText.setText("Good morning, today is " + calendarManager.getDayOfWeek() + "\nYou have " + calendarManager.getAppointmentsTodayCount() + " Appointments.");

            dialogBuilder.setView(dialogView);
            dialogBuilder.setPositiveButton("Thanks", null);

            dialogBuilder.create().show();
        }
    }

    @Override
    protected void onStart() {
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.appointment) {
            calendarManager.removeAppointment(position);
            //todo adapter.setCalendar(calendarManager.getAppointments());
            adapter.notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, NewAppointmentActivity.class);
        startActivity(intent);
    }

    private void initializeAdapters() {
        recyclerView = findViewById(R.id.appointment_list);
        recyclerView.setHasFixedSize(true);

        adapter = new AppointmentAdapter(calendarManager.getAppointments());
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.calendar);
        calendarView.setHasFixedSize(true);

        calendarAdapter = new CalendarAdapter(calendarManager.getPlan());
        calendarAdapter.setClickListener(this);
        calendarView.setLayoutManager(new GridLayoutManager(this, 8));
        calendarView.addItemDecoration(new GridSpacingItemDecoration(8, 2, false));
        calendarView.setAdapter(calendarAdapter);
    }
}


