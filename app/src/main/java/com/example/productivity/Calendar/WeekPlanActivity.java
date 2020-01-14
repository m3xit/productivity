package com.example.productivity.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.productivity.MainActivity;
import com.example.productivity.R;
import java.util.ArrayList;
import java.util.List;

public class WeekPlanActivity extends AppCompatActivity implements AppointmentAdapter.ItemClickListener {

    private List<Appointment> appointments;
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private int requestCodeNewAppointment = 1337;

    public static String AppointmentCreateExtra = "com.example.productivity.WeekPlanActivity.AppointmentCreateExtra";

    private final String appointmentKey = "com.example.productivity.WeekPlanActivity.appointmentKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.calendar);
        setContentView(R.layout.activity_calendar);

        readAppointments();

        initializeAdapters();
    }

    protected void onDestroy() {
        super.onDestroy();

        writeAppointments();
    }

    @Override
    public void onItemClick(View view, int position) {
        appointments.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, appointments.size());
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


