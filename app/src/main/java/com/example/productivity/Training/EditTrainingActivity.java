package com.example.productivity.Training;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;
import com.example.productivity.stuff.VerticalSpaceItemDecoration;

public class EditTrainingActivity extends AppCompatActivity implements EditExerciseAdapter.ItemClickListener {

    private EditText title;
    private RecyclerView exercises;

    private EditExerciseAdapter adapter;

    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_edit);

        setTitle(R.string.edit_training);

        training = TrainingListActivity.trainingManager.getCurrentTraining();

        title = findViewById(R.id.title);
        exercises = findViewById(R.id.exercise_list);

        title.setText(training.getName());
        exercises.addItemDecoration(new VerticalSpaceItemDecoration(20));

        initializeAdapter();
    }

    private void initializeAdapter() {
        exercises.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EditExerciseAdapter(this, training.getExercises());
        adapter.setClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        exercises.setLayoutManager(layoutManager);
        exercises.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                sendBack();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        sendBack();

        super.onBackPressed();
    }

    public void onClick(View view) {
        sendBack();
        finish();
    }

    private void sendBack() {
        if (!title.getText().toString().equals(training.getName())) {
            training.setName(title.getText().toString());
        }

        TrainingListActivity.trainingManager.setCurrentTraining(training);
    }

    private void addSet(int position, String set) {
        training.addSet(position, set);
        adapter.notifyDataSetChanged();
    }

    private void addExercise(String name) {
        training.addExercise(new Exercise(name, ""));
    }

    @Override
    public void onItemClick(View view, final int position) {
        if (position == training.getExercises().size()) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.fragment_edit_text, null);

            final EditText editText = dialogView.findViewById(R.id.text);

            dialogBuilder.setView(dialogView);
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    addExercise(editText.getText().toString());
                }
            });

            dialogBuilder.setMessage("Exercise Name");
            dialogBuilder.create().show();
        } else {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.fragment_edit_text, null);

            final EditText editText = dialogView.findViewById(R.id.text);

            dialogBuilder.setView(dialogView);
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    addSet(position, editText.getText().toString());
                }
            });

            dialogBuilder.setMessage("New Set");
            dialogBuilder.create().show();
        }
    }
}
