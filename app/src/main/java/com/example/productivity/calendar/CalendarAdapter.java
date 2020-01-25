package com.example.productivity.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.productivity.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Appointment[][] calendar;
    private ItemClickListener mClickListener;

    private String[] weekDays = new String[]{"", "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
    private String[] times = new String[]{"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        CalendarViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.calendar_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    CalendarAdapter(Appointment[][] calendar) {
        this.calendar = calendar;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        manageCalendar(holder.title, position);
    }

    @Override
    public int getItemCount() {
        return calendar[0].length * 8 + 8;
    }

    void setClickListener(CalendarAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void manageCalendar(TextView textView, int position) {
        if (position < 8) {
            setTextHeading(textView, weekDays[position]);
        } else if (position % 8 == 0) {
            setTextTime(textView, times[position / 8 - 1]);
        } else {
            setTextAppointment(textView, position);
        }
    }

    private void setTextAppointment(TextView textView, int position) {
        //              position of column + position in the column
        int index = (position - 8) / 8 * 7 + position % 8 - 1;
        Appointment appointment = calendar[index%7][index/7];

        textView.setText(appointment.getName());
        textView.setBackgroundResource(appointment.getCategory().getColor());
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        if (CalendarActivity.calendarManager.sameDayOfWeek(appointment)) {
            textView.setTextColor(Color.BLACK);
            if (CalendarActivity.calendarManager.isNow(index)) {
                textView.setBackgroundResource(R.color.darkGreen);
            }
        } else {
            textView.setTextColor(Color.GRAY);
        }
    }

    private void setTextHeading(TextView textView, String text) {
        textView.setText(text);
        textView.setBackgroundResource(R.color.lighterBackground);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
    }

    private void setTextTime(TextView textView, String text) {
        textView.setText(text);
        textView.setBackgroundResource(R.color.lighterBackground);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
    }
}
