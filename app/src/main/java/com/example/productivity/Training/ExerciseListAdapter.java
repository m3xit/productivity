package com.example.productivity.Training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Exercise> exercises;
    private LayoutInflater mInflater;
    private ExerciseListAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    ExerciseListAdapter(Context context, List<Exercise> data) {
        this.mInflater = LayoutInflater.from(context);
        this.exercises = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.element_exercise, viewGroup, false);
        return new ExerciseListAdapter.ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Exercise exercise = exercises.get(position);
        ((ExerciseListAdapter.ExerciseViewHolder) viewHolder).name.setText(exercise.getName());
        ((ExerciseListAdapter.ExerciseViewHolder) viewHolder).sets.setText(exercise.getSets());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return exercises.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView sets;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exerciseName);
            sets = itemView.findViewById(R.id.exerciseSets);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Exercise getItem(int id) {
        return exercises.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ExerciseListAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

