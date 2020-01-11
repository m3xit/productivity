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
import android.widget.EditText;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private Button pomodoroStart, pomodoroReset, pauseStart, pauseReset, timerStart;
    private TextView pomodoroText, pauseText, statistics;
    private EditText timerMinites;
    private TextView timerText;

    private boolean pomodoroRunning = false, pauseRunning = false, timerRunning = false;
    private static long pomodoroTime = 25*60*1000, pauseTime = 5*60*1000;
    private long pomodoroRemaining, pauseRemaining, timerRemaining;
    private CountDownTimer pomodoro, pause, timer;
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
        timerStart = findViewById(R.id.timer_start1);

        pomodoroText = findViewById(R.id.pomodoroText);
        pauseText = findViewById(R.id.pauseText);
        statistics = findViewById(R.id.statistics);

        timerMinites = findViewById(R.id.timer_number1);
        timerText = findViewById(R.id.timer_text1);

        pomodoro = null;
        pause = null;
        timer = null;

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
        pomodoro.cancel();
        pause.cancel();
        timer.cancel();
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
            case R.id.timer_start1:
                startTimer();
                break;
        }
    }

    private void startTimer() {
        if (!timerRunning) {
            try {
                timerRemaining = Long.parseLong(timerMinites.getText().toString())*1000*60;

                System.out.println(timerRemaining + "*************************");

                timerMinites.setVisibility(View.GONE);
                timerText.setVisibility(View.VISIBLE);

                timer = new CountDownTimer(timerRemaining, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timerRemaining = millisUntilFinished;
                        setRemainingTimeText(timerText, timerRemaining);
                    }

                    public void onFinish() {
                        timerText.setText(R.string.done);
                        timerRunning = false;
                        sendNotofication("Timer finished", "");
                    }
                }.start();

                timerRunning = true;
                timerStart.setText(R.string.reset);
            } catch (NumberFormatException e) {
                timerMinites.setVisibility(View.GONE);
                timerText.setVisibility(View.VISIBLE);
                timerText.setText("Press reset and enter a time");
                timerRunning = true;
                timerStart.setText(R.string.reset);
            }

        } else {
            timerText.setVisibility(View.GONE);
            timerMinites.setVisibility(View.VISIBLE);
            timerMinites.setText(timerRemaining+"");
            timerStart.setText(R.string.start);
            timerRunning = false;
        }
    }

    private void startPomodoro() {
        if (!pomodoroRunning) {
            pomodoro = new CountDownTimer(pomodoroRemaining, 1000) {

                public void onTick(long millisUntilFinished) {
                    pomodoroRemaining = millisUntilFinished;
                    setRemainingTimeText(pomodoroText, pomodoroRemaining);
                }

                public void onFinish() {
                    pomodoroText.setText(R.string.done);
                    pomodoroRunning = false;
                    sendNotofication("Pomodoro finished", "Time for a pause");
                    count ++;
                    setStatistics();
                    writeStatistics();
                }
            }.start();

            pomodoroRunning = true;
            pomodoroReset.setVisibility(View.VISIBLE);
            pomodoroStart.setText(R.string.pause);

        } else {
            pomodoro.cancel();
            pomodoroStart.setText(R.string.resume);
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
        setRemainingTimeText(pomodoroText, pomodoroRemaining);
        pomodoroStart.setText(R.string.start);
    }

    private void startPause() {
        if (!pauseRunning) {
            pause = new CountDownTimer(pauseRemaining, 1000) {

                public void onTick(long millisUntilFinished) {
                    pauseRemaining = millisUntilFinished;
                    setRemainingTimeText(pauseText, pauseRemaining);
                }

                public void onFinish() {
                    pauseText.setText(R.string.done);
                    pauseRunning = false;
                    sendNotofication("Pause finished", "Get back to work");
                }
            }.start();

            pauseRunning = true;
            pauseReset.setVisibility(View.VISIBLE);
            pauseStart.setText(R.string.pause);

        } else {
            pause.cancel();
            pauseStart.setText(R.string.resume);
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
        setRemainingTimeText(pauseText, pauseRemaining);
        pauseStart.setText(R.string.start);
    }

    private void setRemainingTimeText(TextView textView, long time) {
        textView.setText(secondsToString((int) (time/1000)) + " min");
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
