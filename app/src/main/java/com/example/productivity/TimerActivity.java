package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private Button pomodoroStart, pomodoroReset, pauseStart, pauseReset;
    private TextView pomodoroText, pauseText, statistics;
    private boolean pomodoroRunning = false, pauseRunning = false;
    private static long pomodoroTime = 25*60*1000, pauseTime = 5*60*1000;
    private long pomodoroRemaining, pauseRemaining;
    private CountDownTimer pomodoro, pause;
    private String CHANNEL_ID = "com.example.productivity.notification";
    private int notificationId = 1337;
    private int count = 0;
    private final String countKey = "com.example.productivity.countKey";
    private final String settingsKey = "com.example.productivity.countKey";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle(R.string.timer);

        pomodoroStart = findViewById(R.id.pomodoroButtonStart);
        pomodoroReset = findViewById(R.id.pomodoroButtonReset);
        pauseStart = findViewById(R.id.pauseButtonStart);
        pauseReset = findViewById(R.id.pauseButtonReset);

        pomodoroText = findViewById(R.id.pomodoroText);
        pauseText = findViewById(R.id.pauseText);
        statistics = findViewById(R.id.statistics);

        pomodoro = null;
        pause = null;

        resetPomodoro();
        resetPause();

        settings = getApplicationContext().getSharedPreferences(settingsKey, 0);
        getStatistics();
        writeStatistics();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setStatistics();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pomodoroButtonStart:
                startPomodoro();
                break;
            case R.id.pomodoroButtonReset:
                resetPomodoro();
                break;
            case R.id.pauseButtonStart:
                startPause();
                break;
            case R.id.pauseButtonReset:
                startPause();
                break;
        }
    }

    private void startPomodoro() {
        if (!pomodoroRunning) {
            pomodoro = new CountDownTimer(pomodoroRemaining, 1000) {

                public void onTick(long millisUntilFinished) {
                    pomodoroRemaining = millisUntilFinished;
                    pomodoroSetText();
                }

                public void onFinish() {
                    pomodoroText.setText("Done!");
                    pomodoroRunning = false;
                    sendNotofication("Pomodoro finished", "Time for a pause");
                    count ++;
                    setStatistics();
                    writeStatistics();
                }
            }.start();

            pomodoroRunning = true;
            pomodoroReset.setVisibility(View.VISIBLE);
            pomodoroStart.setText("Pause");

        } else {
            pomodoro.cancel();
            pomodoroStart.setText("Resume");
            pomodoroRunning = false;
        }
    }

    private void resetPomodoro() {
        if (pomodoro != null) {
            pomodoro.cancel();
        }
        pomodoroRunning = false;
        pomodoroRemaining = pomodoroTime;
        pomodoroReset.setVisibility(View.GONE);
        pomodoroText.setText(secondsToString((int) (pomodoroRemaining / 1000)) + " min");
        pomodoroStart.setText("Start");
    }

    private void startPause() {
        if (!pauseRunning) {
            pause = new CountDownTimer(pauseRemaining, 1000) {

                public void onTick(long millisUntilFinished) {
                    pauseRemaining = millisUntilFinished;
                    pauseSetText();
                }

                public void onFinish() {
                    pauseText.setText("Done!");
                    pauseRunning = false;
                    sendNotofication("Pause finished", "Get back to work");
                }
            }.start();

            pauseRunning = true;
            pauseReset.setVisibility(View.VISIBLE);
            pauseStart.setText("Pause");

        } else {
            pause.cancel();
            pauseStart.setText("Resume");
            pauseRunning = false;
        }
    }

    private void resetPause() {
        if (pause != null) {
            pause.cancel();
        }
        pauseRunning = false;
        pauseRemaining = pauseTime;
        pauseReset.setVisibility(View.GONE);
        pauseSetText();
        pauseStart.setText("Start");
    }

    private void pomodoroSetText() {
        pomodoroText.setText(secondsToString((int) (pomodoroRemaining/1000)) + " min");
    }

    private void pauseSetText() {
        pauseText.setText(secondsToString((int) (pauseRemaining/1000)) + " min");
    }

    private String secondsToString(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    private void sendNotofication(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            CharSequence name = "test channel";
            String description = "test description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void getStatistics() {
        count = settings.getInt(countKey, 0);
    }

    private void writeStatistics() {
        String text = "Total Pomodoros: " + count + "\n";
        text += "Total Time: " + count/2.0 + "h\n";
        text += "Pomodoros today: " + count + "\n";
        statistics.setText(text);
    }

    private void setStatistics() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(settingsKey, count);
        editor.apply();
    }
}
