package com.example.productivity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productivity.Calendar.CalendarActivity;
import com.example.productivity.Notes.NotesActivity;
import com.example.productivity.Timer.TimerActivity;
import com.example.productivity.Training.TrainingListActivity;
import com.example.productivity.stuff.SaveData;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST = 1337;
    private boolean hasCameraFlash = false, flashLightStatus = false;

    public static SaveData store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        store = new SaveData(getApplicationContext());
    }

    public void onClick(View view) {
        System.out.println("ID: " + view.getId());
        switch (view.getId()) {
            case R.id.button_todo:
                Intent intent_todo = new Intent(this, NotesActivity.class);
                startActivity(intent_todo);
                break;
            case R.id.button_training:
                Intent intent_training = new Intent(this, TrainingListActivity.class);
                startActivity(intent_training);
                break;
            case R.id.button_plan:
                Intent intent_plan = new Intent(this, CalendarActivity.class);
                startActivity(intent_plan);
                break;
            case R.id.button_pomodoro:
                Intent intent_pomodoro = new Intent(this, TimerActivity.class);
                startActivity(intent_pomodoro);
                break;
            case R.id.action_button1:
                final Intent intentDeviceTest = new Intent("com.bambuna.podcastaddict.service.player.toggle");
                intentDeviceTest.setComponent(new  ComponentName("com.bambuna.podcastaddict","com.bambuna.podcastaddict.receiver.PodcastAddictPlayerReceiver"));
                sendBroadcast(intentDeviceTest);
                break;
            case R.id.action_button2:
                if (hasCameraFlash && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (flashLightStatus)
                        flashLightOff();
                    else
                        flashLightOn();
                }
                break;
        }
    }

    @TargetApi(23)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            flashLightStatus = true;
        } catch (CameraAccessException e) {

        }
    }

    @TargetApi(23)
    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
        } catch (CameraAccessException e) {

        }
    }
}
