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

public class TrainingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Training> trainings;
    private LayoutInflater mInflater;
    private ItemClickListener clickListener;
    private ItemLongClickListener longClickListener;

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
            view = mInflater.inflate(R.layout.element_list_cardio, viewGroup, false);
            return new CardioViewHolder(view);

        } else if (viewType == TrainingType.GYM.ordinal()) {
            view = mInflater.inflate(R.layout.element_list_gym, viewGroup, false);
            return new GymViewHolder(view);
        } else if (viewType == TrainingType.CLIMBING.ordinal()) {
            view = mInflater.inflate(R.layout.element_list_cardio, viewGroup, false);
            return new ClimbingViewHolder(view);
        } else if (viewType == TrainingType.OTHER.ordinal()) {
            view = mInflater.inflate(R.layout.element_list_cardio, viewGroup, false);
            return new OtherViewHolder(view);
        } else {
            throw new TypeNotPresentException("unkows training type", null);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Training training = trainings.get(position);
        ((TrainingViewHolder) viewHolder).title.setText(training.getName());
        ((TrainingViewHolder) viewHolder).date.setText(training.getDate());
        ((TrainingViewHolder) viewHolder).duration.setText(training.getDuration() + " min");
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public abstract class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView title;
        TextView date;
        TextView duration;

        TrainingViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.training_title);
            date = view.findViewById(R.id.training_date);
            duration = view.findViewById(R.id.training_duration);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (longClickListener != null) {
                return longClickListener.onItemLongClick(view, getAdapterPosition());
            } else return false;
        }
    }

    public class GymViewHolder extends TrainingViewHolder {

        GymViewHolder(View view) {
            super(view);
        }
    }

    public class CardioViewHolder extends TrainingViewHolder {

        CardioViewHolder(View view) {
            super(view);
        }
    }

    public class ClimbingViewHolder extends TrainingViewHolder {

        ClimbingViewHolder(View view) {
            super(view);
            ImageView image = view.findViewById(R.id.todoImageView);
            image.setImageResource(R.drawable.ic_terrain_black_24dp);
        }
    }

    public class OtherViewHolder extends TrainingViewHolder {

        OtherViewHolder(View view) {
            super(view);
            ImageView image = view.findViewById(R.id.todoImageView);
            image.setImageResource(R.drawable.ic_add_black_24dp);
        }
    }

    Training getItem(int id) {
        return trainings.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.longClickListener = itemLongClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}