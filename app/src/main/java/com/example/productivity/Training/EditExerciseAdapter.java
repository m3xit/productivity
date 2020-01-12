package com.example.productivity.Training;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class EditExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Exercise> exercises;
    private LayoutInflater mInflater;
    private EditExerciseAdapter.ItemClickListener mClickListener;

    private int TYPE_EXERCISE = 0;
    private int TYPE_END = 1;

    // data is passed into the constructor
    EditExerciseAdapter(Context context, List<Exercise> data) {
        this.mInflater = LayoutInflater.from(context);
        this.exercises = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_END) {
            View view = mInflater.inflate(R.layout.exercise_edit_end_element, viewGroup, false);
            return new EditExerciseEndViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.exercise_edit_element, viewGroup, false);
            return new EditExerciseViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position == getItemCount()-1) {
            //EditExerciseEndViewHolder
        } else {
            Exercise exercise = exercises.get(position);
            ((EditExerciseAdapter.EditExerciseViewHolder) viewHolder).name.setText(exercise.getName());
            ((EditExerciseAdapter.EditExerciseViewHolder) viewHolder).sets.setText(exercise.getSets());
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return exercises.size()+1;
    }

    // stores and recycles views as they are scrolled off screen
    public class EditExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView sets;

        EditExerciseViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            sets = itemView.findViewById(R.id.exercise_sets);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class EditExerciseEndViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button add;

        EditExerciseEndViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add_exercise);
            add.setOnClickListener(this);
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
    void setClickListener(EditExerciseAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return TYPE_END;
        } else {
            return TYPE_EXERCISE;
        }
    }
}
