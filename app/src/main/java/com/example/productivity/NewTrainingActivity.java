package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTrainingActivity extends AppCompatActivity {

    Button button;
    TrainingType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
        setTitle("Create a new Training");
        button = findViewById(R.id.button_gym);
        type = TrainingType.GYM;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            Intent data = new Intent();
            String text = ((EditText)findViewById(R.id.editText)).getText().toString();
            Training training = new Training(text, type);
            data.putExtra(TrainingListActivity.TrainingCreateExtra, training);
            setResult(RESULT_OK, data);
            finish();
        } else if (view.getId() == R.id.button_gym) {
            String text = button.getText().toString();

            switch (text) {
                case "Gym":
                    button.setText("Cardio");
                    type = TrainingType.CARDIO;
                    break;
                case "Cardio":
                    button.setText("Climbing");
                    type = TrainingType.CLIMBING;
                    break;
                case "Climbing":
                    button.setText("Gym");
                    type = TrainingType.GYM;
                    break;
            }
        }

    }
}
