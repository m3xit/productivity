package com.example.productivity.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.EditText;

import com.example.productivity.R;

public class NewAppointmentActivity extends AppCompatActivity {

    private Button date, time;
    private EditText name;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        name = findViewById(R.id.name);

        mYear = 2020;
        mMonth = 0;
        mDay = 8;

        mHour = 12;
        mMinute = 0;

        date.setText(mDay + "." + mMonth + 1 + "." + mYear);

        time.setText(mHour + ":" + mMinute);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.date) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;

                            date.setText(mDay + "." + mMonth + 1 + "." + mYear);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view.getId() == R.id.time) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mHour = hourOfDay;
                            mMinute = minute;

                            time.setText(mHour + ":" + mMinute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (view.getId() == R.id.done) {
            Intent data = new Intent();
            Appointment appointment = new Appointment(name.getText().toString(), mYear, mMonth, mDay, mHour, mMinute);
            data.putExtra(WeekPlanActivity.AppointmentCreateExtra, appointment);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
