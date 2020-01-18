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
    private RecyclerView templateExercises;

    private EditExerciseAdapter adapter;
    private EditExerciseAdapter templateAdapter;

    private Training training, template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_edit);

        setTitle(R.string.edit_training);

        training = TrainingListActivity.trainingManager.getCurrentTrainingId();

        title = findViewById(R.id.title);

        exercises = findViewById(R.id.exercise_list);
        adapter = new EditExerciseAdapter(this, training.getExercises(), true);
        initializeAdapter(adapter, exercises);

        title.setText(training.getName());
        exercises.addItemDecoration(new VerticalSpaceItemDecoration(20));

        template();
    }

    private void template() {
        template = TrainingListActivity.trainingManager.getTemplate();

        if (template != null) {
            templateExercises = findViewById(R.id.template_list);
            templateExercises.setVisibility(View.VISIBLE);
            templateExercises.addItemDecoration(new VerticalSpaceItemDecoration(20));
            EditExerciseAdapter templateAdapter = new EditExerciseAdapter(this, template.getExercises(), false);
            initializeAdapter(templateAdapter, templateExercises);
        }
    }

    private void initializeAdapter(EditExerciseAdapter exerciseAdapter, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter.setClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(exerciseAdapter);
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
        if (view.getId() == R.id.save) {
            sendBack();
            finish();
        } else if (view.getId() == R.id.add) {
            addExercise();
        }
    }

    private void addSet(final int position) {
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

    private void addExercise() {
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
    }

    private void removeExercise(int position) {
        training.removeExercise(position);
        adapter.notifyDataSetChanged();
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
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.imageView) {
            removeExercise(position);
        } else {
            addSet(position);
        }
    }
}
