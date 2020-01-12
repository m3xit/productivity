package com.example.productivity.Training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.productivity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TrainingListActivity extends AppCompatActivity implements TrainingListAdapter.ItemClickListener, TrainingListAdapter.ItemLongClickListener {

    TrainingListAdapter adapter;
    List<Training> trainings;
    RecyclerView recyclerView;
    static int requestCodeNewTraining = 1;
    public static String TrainingCreateExtra = "com.example.productivity.TrainingCreateExtra";
    public static String TrainingViewExtra = "com.example.productivity.TrainingViewExtra";

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

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainingListAdapter(this, trainings);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeNewTraining) {
            if (resultCode == RESULT_OK) {
                Training newTraining = (Training) data.getExtras().get(TrainingCreateExtra);
                trainings.add(0, newTraining);
                adapter.notifyDataSetChanged();

                writeTrainings();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TrainingDetailsActivity.class);
        intent.putExtra(TrainingViewExtra, trainings.get(position));
        startActivity(intent);
    }

    private void readTrainings() {
        try {
            FileInputStream fis = this.openFileInput("trainings");
            ObjectInputStream is = new ObjectInputStream(fis);
            trainings = (List<Training>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void writeTrainings() {
        try {
            FileOutputStream fos = this.openFileOutput("trainings", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(trainings);
            os.close();
            fos.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
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

class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    VerticalSpaceItemDecoration(int verticalSpaceHeight) {
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
