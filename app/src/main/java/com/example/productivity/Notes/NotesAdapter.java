package com.example.productivity.Notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes;
    private NotesAdapter.ItemClickListener mClickListener;

    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView body;
        public NotesViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            body = v.findViewById(R.id.body);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_notes, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        if (position == getItemCount()-1) {
            //EndViewHolder
        } else {
            holder.title.setText(notes.get(position).getTitle());
            holder.body.setText(notes.get(position).getBody());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size()+1;
    }

    void setClickListener(NotesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
