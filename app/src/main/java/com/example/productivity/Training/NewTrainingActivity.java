package com.example.productivity.Training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.productivity.R;

public class NewTrainingActivity extends AppCompatActivity {

    private Button gym, cardio, climbing, other;
    private TrainingType type;
    private Drawable buttonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
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
            Intent data = new Intent();
            String name = ((EditText)findViewById(R.id.editText)).getText().toString();
            Training training = new Training(name, type);
            data.putExtra(TrainingListActivity.TrainingCreateExtra, training);
            setResult(RESULT_OK, data);
            finish();
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
