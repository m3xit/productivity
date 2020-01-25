package com.example.productivity.notes;

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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList;
    private TodoAdapter.ItemClickListener clickListener;
    private TextWatcher textWatcher;
    private OnCheckedChangeListener checkListener;
    private final int TYPE_TODO = 0;
    private final int TYPE_END = 1;
    public static int TYPE = 0;

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, android.text.TextWatcher, CompoundButton.OnCheckedChangeListener {
        EditText title;
        ImageView image;
        CheckBox checkBox;

        TodoViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            title.setFocusable(false);
            //title.addTextChangedListener(this);

            image = v.findViewById(R.id.todoImageView);
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
            if (textWatcher != null) textWatcher.afterTextChanged(s, getAdapterPosition(), TYPE);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(checkListener != null) checkListener.onCheckedChanged(buttonView, isChecked, getAdapterPosition(), TYPE);
        }

        public void setUnchecked() {
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener(this);
        }
    }

    TodoAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        if (position == getItemCount()-1) {
            holder.image.setImageResource(R.drawable.ic_add_black_24dp);
            System.out.println(position + "PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        } else {
            holder.title.setText(todoList.get(position).getTitle());
        }
        holder.setUnchecked();
    }

    @Override
    public int getItemCount() {
        return todoList.size()+1;
    }

    void setClickListener(TodoAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    void setCheckListener(OnCheckedChangeListener listener) {
        this.checkListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface TextWatcher {
        void afterTextChanged(Editable s, int position, int type);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position, int type);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return TYPE_END;
        } else {
            return TYPE_TODO;
        }
    }
}