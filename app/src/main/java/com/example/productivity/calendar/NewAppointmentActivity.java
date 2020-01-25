package com.example.productivity.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.EditText;

import com.example.productivity.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewAppointmentActivity extends AppCompatActivity {

    private Button dateButton, timeButton;
    private EditText name;
    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_new_appointment);

        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.time);
        name = findViewById(R.id.name);

        date = GregorianCalendar.getInstance();

        dateButton.setText(new SimpleDateFormat("dd.MM.yyyy").format(date.getTime()));

        timeButton.setText(new SimpleDateFormat("HH:mm").format(date.getTime()));
    }

    public void onClick(View view) {

        if (view.getId() == R.id.date) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.set(Calendar.YEAR, year);
                            date.set(Calendar.MONTH, monthOfYear);
                            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            dateButton.setText(new SimpleDateFormat("dd.MM.yyyy").format(date.getTime()));
                        }
                    }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
        if (view.getId() == R.id.time) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            date.set(Calendar.HOUR, hourOfDay);
                            date.set(Calendar.MINUTE, minute);

                            timeButton.setText(new SimpleDateFormat("HH:mm").format(date.getTime()));
                        }
                    }, date.get(Calendar.HOUR), date.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }

        if (view.getId() == R.id.done) {
            String appointmentName = name.getText().toString().trim();

            if (!appointmentName.equals("")) {
                Appointment appointment = new Appointment(appointmentName, date, AppointmentCategory.Other);
                CalendarActivity.calendarManager.addAppointment(appointment);
            }

            finish();
        }
    }
}
