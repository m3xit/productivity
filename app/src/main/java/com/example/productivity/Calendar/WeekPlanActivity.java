package com.example.productivity.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.productivity.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WeekPlanActivity extends AppCompatActivity implements AppointmentAdapter.ItemClickListener {

    private Button addAppointment;
    private List<Appointment> appointments;
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private int requestCodeNewAppointment = 1337;
    public static String AppointmentCreateExtra = "com.example.productivity.AppointmentCreateExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.week_plan);
        setContentView(R.layout.activity_calendar);

        addAppointment = findViewById(R.id.add_appointment);

        appointments = new ArrayList<Appointment>();

        try {
            FileInputStream fis = this.openFileInput("appointments");
            ObjectInputStream is = new ObjectInputStream(fis);
            appointments = (List<Appointment>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

//        appointments.add(new Appointment("Seelos", 2020, 1, 8, 8, 45));
//        appointments.add(new Appointment("Burkart", 2020, 1, 9, 8, 45));
//        appointments.add(new Appointment("Heidenreich", 2020, 1, 10, 8, 45));

        System.out.println("********************");
        for (Appointment a : appointments) {
            System.out.println(a.getName() + " " + a.getDate());
        }

        recyclerView = (RecyclerView) findViewById(R.id.appointment_list);

        recyclerView.setHasFixedSize(true);

        adapter = new AppointmentAdapter(appointments);
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    protected void onDestroy() {
        super.onDestroy();

        save();
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("**************************+");
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
                save();
            }
        }
    }

    private void save() {
        try {
            FileOutputStream fos = this.openFileOutput("appointments", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(appointments);
            os.close();
            fos.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}


