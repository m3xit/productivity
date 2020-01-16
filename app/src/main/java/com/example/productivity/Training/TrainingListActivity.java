package com.example.productivity.Training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.productivity.MainActivity;
import com.example.productivity.R;
import com.example.productivity.stuff.VerticalSpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingListActivity extends AppCompatActivity implements TrainingListAdapter.ItemClickListener, TrainingListAdapter.ItemLongClickListener {

    TrainingListAdapter adapter;
    List<Training> trainings;
    RecyclerView recyclerView;

    static int requestCodeNewTraining = 0;
    static int requestCodeViewTraining = 1;

    public static String TrainingCreateExtra = "com.example.productivity.TrainingListActivity.TrainingCreateExtra";
    public static String TrainingViewExtra = "com.example.productivity.TrainingListActivity.TrainingViewExtra";

    private final String trainingsKey = "com.example.productivity.TrainingListActivity.trainingKey";

    private int trainingViewed = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        setTitle(R.string.training);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewTrainingActivity.class);
                startActivityForResult(intent, requestCodeNewTraining);
            }
        });

        trainings = new ArrayList<>();

        readTrainings();

        initializeAdapters();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == requestCodeNewTraining) {
                Training newTraining = (Training) data.getExtras().get(TrainingCreateExtra);
                trainings.add(0, newTraining);
                adapter.notifyItemInserted(0);
                adapter.notifyItemRangeChanged(0, trainings.size());
                writeTrainings();

            } else if (requestCode == requestCodeViewTraining) {
                Training newTraining = (Training) data.getExtras().get(TrainingViewExtra);
                trainings.set(trainingViewed, newTraining);
                adapter.notifyItemChanged(trainingViewed);
                System.out.println("****************************");
                writeTrainings();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, TrainingDetailsActivity.class);
        intent.putExtra(TrainingViewExtra, trainings.get(position));
        trainingViewed = position;
        startActivityForResult(intent, requestCodeViewTraining);
    }

    private void initializeAdapters() {
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainingListAdapter(this, trainings);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
    }

    private void readTrainings() {
        trainings = (List<Training>) MainActivity.store.read(trainingsKey);

        if (trainings == null) {
            trainings = new ArrayList<>();
        }
    }

    private void writeTrainings() {
        MainActivity.store.write(trainingsKey, trainings);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        trainings.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, trainings.size());
        writeTrainings();
        return true;
    }
}

