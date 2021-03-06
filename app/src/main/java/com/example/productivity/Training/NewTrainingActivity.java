package com.example.productivity.Training;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.productivity.R;

public class NewTrainingActivity extends AppCompatActivity {

    private Button gym, cardio, climbing, other;
    private TrainingType type;
    private Drawable buttonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_new);
        setTitle("Create a new Training");

        gym = findViewById(R.id.button_gym);
        cardio = findViewById(R.id.button_cardio);
        climbing = findViewById(R.id.button_climbing);
        other = findViewById(R.id.button_other);

        buttonDefault = gym.getBackground();
        changeType(TrainingType.GYM);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.start_training) {
            if (type == TrainingType.GYM) {
                Intent intent = new Intent(this, EditTrainingActivity.class);
                String name = ((EditText) findViewById(R.id.editText)).getText().toString();
                TrainingListActivity.trainingManager.startTraining(name, type);
                startActivity(intent);
            } else {
                String name = ((EditText) findViewById(R.id.editText)).getText().toString();
                TrainingListActivity.trainingManager.startTraining(name, type);
                finish();
            }

        } else if (view.getId() == R.id.button_gym) {
            changeType(TrainingType.GYM);
        } else if (view.getId() == R.id.button_cardio) {
            changeType(TrainingType.CARDIO);
        } else if (view.getId() == R.id.button_climbing) {
            changeType(TrainingType.CLIMBING);
        } else if (view.getId() == R.id.button_other) {
            changeType(TrainingType.OTHER);
        }
    }

    private void changeType(TrainingType newType) {
        if (type != null) {
            switch (type) {
                case GYM:
                    gym.setBackground(buttonDefault);
                    break;
                case CARDIO:
                    cardio.setBackground(buttonDefault);
                    break;
                case CLIMBING:
                    climbing.setBackground(buttonDefault);
                    break;
                case OTHER:
                    other.setBackground(buttonDefault);
                    break;
            }
        }

        type = newType;

        switch (type) {
            case GYM:
                gym.setBackground(getDrawable(R.color.darkRed));
                break;
            case CARDIO:
                cardio.setBackground(getDrawable(R.color.darkRed));
                break;
            case CLIMBING:
                climbing.setBackground(getDrawable(R.color.darkRed));
                break;
            case OTHER:
                other.setBackground(getDrawable(R.color.darkRed));
                break;
        }
    }
}
