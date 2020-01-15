package com.example.productivity.Notes;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder> {
    private List<Todo> todoList;
    private DoneAdapter.ItemClickListener clickListener;
    private TextWatcher textWatcher;
    private OnCheckedChangeListener checkListener;

    public class DoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, android.text.TextWatcher, CompoundButton.OnCheckedChangeListener {
        EditText title;
        ImageView image;
        CheckBox checkBox;

        DoneViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            title.addTextChangedListener(this);

            image = v.findViewById(R.id.todoImageView);
            image.setImageResource(R.drawable.ic_clear_black_24dp);
            image.setOnClickListener(this);

            checkBox = v.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (textWatcher != null) textWatcher.beforeTextChanged(s, start, count, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (textWatcher != null) textWatcher.onTextChanged(s, start, before, count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (textWatcher != null) textWatcher.afterTextChanged(s, getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(checkListener != null) checkListener.onCheckedChanged(buttonView, isChecked, getAdapterPosition());
        }
    }

    DoneAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public DoneAdapter.DoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_todo, parent, false);
        return new DoneAdapter.DoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoneAdapter.DoneViewHolder holder, int position) {
        holder.title.setText(todoList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return todoList.size()+1;
    }

    void setClickListener(DoneAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    void setTextWatcher(DoneAdapter.TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    void setCheckListener(DoneAdapter.OnCheckedChangeListener listener) {
        this.checkListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface TextWatcher {
        void afterTextChanged(Editable s, int position);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position);
    }
}
