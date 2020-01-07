package com.example.productivity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Training> trainings;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    TrainingListAdapter(Context context, List<Training> data) {
        this.mInflater = LayoutInflater.from(context);
        this.trainings = data;
    }

    @Override
    public int getItemViewType(int position) {
        return trainings.get(position).getType().ordinal();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TrainingType.CARDIO.ordinal()) {
            view = mInflater.inflate(R.layout.list_cardio_element, viewGroup, false);
            return new CardioViewHolder(view);

        } else if (viewType == TrainingType.GYM.ordinal()) {
            view = mInflater.inflate(R.layout.list_gym_element, viewGroup, false);
            return new GymViewHolder(view);
        } else if (viewType == TrainingType.CLIMBING.ordinal()) {
            view = mInflater.inflate(R.layout.list_cardio_element, viewGroup, false);
            return new ClimbingViewHolder(view);
        } else {
            throw new TypeNotPresentException("unkows training type", null);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Training training = trainings.get(position);
        if (getItemViewType(position) == TrainingType.CARDIO.ordinal()) {
            ((CardioViewHolder) viewHolder).title.setText(training.getName());
            ((CardioViewHolder) viewHolder).date.setText(training.getDate());
            ((CardioViewHolder) viewHolder).duration.setText(training.getDuration() + " min");
        } else if (getItemViewType(position) == TrainingType.GYM.ordinal()) {
            ((GymViewHolder) viewHolder).title.setText(training.getName());
            ((GymViewHolder) viewHolder).date.setText(training.getDate());
            ((GymViewHolder) viewHolder).duration.setText(training.getDuration() + " min");
        } else if (getItemViewType(position) == TrainingType.CLIMBING.ordinal()) {
            ((ClimbingViewHolder) viewHolder).title.setText(training.getName());
            ((ClimbingViewHolder) viewHolder).date.setText(training.getDate());
            ((ClimbingViewHolder) viewHolder).duration.setText(training.getDuration() + " min");
        }
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class GymViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        TextView date;
        TextView duration;

        GymViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.training_title);
            date = itemView.findViewById(R.id.training_date);
            duration = itemView.findViewById(R.id.training_duration);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            trainings.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), trainings.size());
            return true;
        }
    }

    public class CardioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        TextView date;
        TextView duration;

        CardioViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.training_title);
            date = itemView.findViewById(R.id.training_date);
            duration = itemView.findViewById(R.id.training_duration);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            trainings.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), trainings.size());
            return true;
        }
    }

    public class ClimbingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        TextView date;
        TextView duration;

        ClimbingViewHolder(View itemView) {
            super(itemView);
            ImageView image = itemView.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.ic_terrain_black_24dp);
            title = itemView.findViewById(R.id.training_title);
            date = itemView.findViewById(R.id.training_date);
            duration = itemView.findViewById(R.id.training_duration);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            trainings.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), trainings.size());
            return true;
        }
    }

    Training getItem(int id) {
        return trainings.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}