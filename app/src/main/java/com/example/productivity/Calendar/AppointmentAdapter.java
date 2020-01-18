package com.example.productivity.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private List<Appointment> appointments;
    private AppointmentAdapter.ItemClickListener mClickListener;

    public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView date;
        public AppointmentViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.appointment_name);
            date = v.findViewById(R.id.appointment_date);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        holder.name.setText(appointments.get(position).getName());
        holder.date.setText(appointments.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    void setClickListener(AppointmentAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
