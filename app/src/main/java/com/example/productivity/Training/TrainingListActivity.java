package com.example.productivity.Training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;
import com.example.productivity.stuff.VerticalSpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrainingListActivity extends AppCompatActivity implements TrainingListAdapter.ItemClickListener, TrainingListAdapter.ItemLongClickListener {

    TrainingListAdapter adapter;
    RecyclerView recyclerView;

    public static TrainingManager trainingManager;

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
                startActivity(intent);
            }
        });

        trainingManager = new TrainingManager();

        recyclerView = findViewById(R.id.list);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        initializeAdapters();
    }

    @Override
    protected void onStart() {
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, TrainingDetailsActivity.class);
        trainingManager.startTraining(position);
        startActivity(intent);
    }

    private void initializeAdapters() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainingListAdapter(this, trainingManager.getTrainings());
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        trainingManager.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, trainingManager.getSize());
        return true;
    }
}

