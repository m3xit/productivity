package com.example.productivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productivity.Calendar.WeekPlanActivity;
import com.example.productivity.Notes.ToDoActivity;
import com.example.productivity.Training.TimerActivity;
import com.example.productivity.Training.TrainingListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        System.out.println("ID: " + view.getId());
        switch (view.getId()) {
            case R.id.button_todo:
                Intent intent_todo = new Intent(this, ToDoActivity.class);
                startActivity(intent_todo);
                break;
            case R.id.button_training:
                Intent intent_training = new Intent(this, TrainingListActivity.class);
                startActivity(intent_training);
                break;
            case R.id.button_plan:
                Intent intent_plan = new Intent(this, WeekPlanActivity.class);
                startActivity(intent_plan);
                break;
            case R.id.button_pomodoro:
                Intent intent_pomodoro = new Intent(this, TimerActivity.class);
                startActivity(intent_pomodoro);
                break;
        }
    }

}
