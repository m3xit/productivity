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
    private List<Todo> doneList;
    private DoneAdapter.ItemClickListener clickListener;
    private TextWatcher textWatcher;
    private OnCheckedChangeListener checkListener;
    public static int ID_CANCEL = R.id.notes + R.id.todoImageView;
    public static int TYPE = 1;

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
            image.setId(ID_CANCEL);
            image.setOnClickListener(this);

            checkBox = v.findViewById(R.id.checkBox);
            checkBox.setChecked(true);
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

        public void setChecked() {
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(true);
            checkBox.setOnCheckedChangeListener(this);
        }
    }

    DoneAdapter(List<Todo> doneList) {
        this.doneList = doneList;
    }

    @Override
    public DoneAdapter.DoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_todo, parent, false);
        return new DoneAdapter.DoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoneAdapter.DoneViewHolder holder, int position) {
        if (doneList.size() == 0) {
            holder.title.setText("No finished Items");
            holder.title.setEnabled(false);
            //holder.title.setBackground(null);
            holder.image.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.GONE);
        } else if (doneList.size() == 1) {
            holder.title.setText(doneList.get(position).getTitle());
            holder.title.setEnabled(true);
            //holder.title.setBackgroundColor(Color.BLACK);
            holder.image.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.title.setText(doneList.get(position).getTitle());
        }
        holder.setChecked();
    }

    @Override
    public int getItemCount() {
        return (doneList.size() > 0 ? doneList.size() : 1);
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
        void afterTextChanged(Editable s, int position, int type);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position, int type);
    }
}
