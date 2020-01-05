package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity implements TrainingListAdapter.ItemClickListener {

    TrainingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setTitle(R.string.training);

        List<Training> trainings = new ArrayList<>();

        trainings.add(new Training("Pull", TrainingType.GYM));
        trainings.add(new Training("Push", TrainingType.GYM));
        trainings.add(new Training("Beine", TrainingType.GYM));
        trainings.add(new Training("Joggen", TrainingType.CARDIO));
        trainings.add(new Training("Pull", TrainingType.GYM));
        trainings.add(new Training("Push", TrainingType.GYM));
        trainings.add(new Training("Beine", TrainingType.GYM));
        trainings.add(new Training("Joggen Alena", TrainingType.CARDIO));
        trainings.add(new Training("Joggen", TrainingType.CARDIO));
        trainings.add(new Training("Pull", TrainingType.GYM));
        trainings.add(new Training("Push", TrainingType.GYM));
        trainings.add(new Training("Beine", TrainingType.GYM));
        trainings.add(new Training("Joggen", TrainingType.CARDIO));

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainingListAdapter(this, trainings);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}

class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
