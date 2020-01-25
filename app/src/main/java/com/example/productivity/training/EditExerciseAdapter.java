package com.example.productivity.training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class EditExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Exercise> exercises;
    private LayoutInflater inflater;
    private boolean editable;
    private EditExerciseAdapter.ItemClickListener clickListener;

    EditExerciseAdapter(Context context, List<Exercise> data, boolean editable) {
        this.inflater = LayoutInflater.from(context);
        this.exercises = data;
        this.editable = editable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.element_exercise_edit, viewGroup, false);
        return new EditExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Exercise exercise = exercises.get(position);
        ((EditExerciseAdapter.EditExerciseViewHolder) viewHolder).name.setText(exercise.getName());
        ((EditExerciseAdapter.EditExerciseViewHolder) viewHolder).sets.setText(exercise.getSets());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class EditExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView sets;
        ImageView imageView;

        EditExerciseViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            sets = itemView.findViewById(R.id.exercise_sets);
            imageView = itemView.findViewById(R.id.imageView);

            if (editable) {
                imageView.setOnClickListener(this);
            } else {
                imageView.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Exercise getItem(int id) {
        return exercises.get(id);
    }

    void setClickListener(EditExerciseAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
