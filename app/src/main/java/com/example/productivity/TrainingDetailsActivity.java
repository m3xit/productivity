package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        setTitle(training.getName() + " am " + training.getDate());

        training.addExercise(new Exercise("Bench", "10x12,5\n5x20\n10x17,5"));
        training.addExercise(new Exercise("Butterfly", "10x12,5\n5x20\n10x17,5"));
        training.addExercise(new Exercise("Schulter", "10x12,5\n5x20\n10x17,5"));
        training.addExercise(new Exercise("Trizeps", "10x12,5\n5x20\n10x17,5"));
        training.addExercise(new Exercise("Rückenstrecker", "10x12,5\n5x20\n10x17,5"));

        exercises = training.getExercises();

        titleView = findViewById(R.id.statistics);
        setTitle();

        recyclerView = findViewById(R.id.exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseListAdapter(this, exercises);
        adapter.setClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(20));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void setTitle() {
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

class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int horizontalSpaceHeight;

    public HorizontalSpaceItemDecoration(int horizontalSpaceHeight) {
        this.horizontalSpaceHeight = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = horizontalSpaceHeight;
        }
    }
}