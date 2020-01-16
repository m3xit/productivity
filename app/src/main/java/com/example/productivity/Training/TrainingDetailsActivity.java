package com.example.productivity.Training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.productivity.R;
import com.example.productivity.stuff.HorizontalSpaceItemDecoration;

import java.util.List;

public class TrainingDetailsActivity extends AppCompatActivity implements ExerciseListAdapter.ItemClickListener {

    Training training;
    List<Exercise> exercises;
    RecyclerView recyclerView;
    ExerciseListAdapter adapter;
    TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        training = (Training) getIntent().getExtras().get(TrainingListActivity.TrainingViewExtra);

        exercises = training.getExercises();

        titleView = findViewById(R.id.statistics);
        setTitle();

        initializeAdapter();

        //don't add spacer after every edit
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(20));
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, EditTrainingActivity.class);
        intent.putExtra(TrainingListActivity.TrainingCreateExtra, training);
        startActivityForResult(intent, TrainingListActivity.requestCodeNewTraining);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TrainingListActivity.requestCodeNewTraining) {
            if (resultCode == RESULT_OK) {
                training = (Training) data.getExtras().get(TrainingListActivity.TrainingCreateExtra);
                exercises = training.getExercises();

                System.out.println(training.getName() + " " + exercises.size());
                setTitle();
                initializeAdapter();

                setResult();
            }
        }
    }

    private void setResult() {
        Intent result = new Intent();
        result.putExtra(TrainingListActivity.TrainingViewExtra, training);
        setResult(RESULT_OK, result);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void initializeAdapter() {
        recyclerView = findViewById(R.id.exercise_list);
        adapter = new ExerciseListAdapter(this, exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setTitle() {
        setTitle(training.getName() + " am " + training.getDate());

        String title =   "Anzahl Übungen:\t\t" + exercises.size() + "\n";
        title +=         "Trainingsdauer:\t\t" + training.getDuration() + " min\n";
        switch (training.getType()) {
            case GYM:
                title += "Trainingstyp:\t\t Kraftraum";
                break;
            case CARDIO:
                title += "Trainingstyp:\t\t Cardio";
                break;
            case CLIMBING:
                title += "Trainingstyp:\t\t Klettern/Bouldern";
                break;
        }

        titleView.setText(title);
    }
}
