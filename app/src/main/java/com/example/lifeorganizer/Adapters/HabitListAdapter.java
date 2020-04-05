package com.example.lifeorganizer.Adapters;

import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.ViewHolder> {

    private ArrayList<Habit> habitsList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ConstraintLayout mConstraintLayout;

        public ViewHolder(ConstraintLayout v) {
            super(v);
            mConstraintLayout = v;
        }
    }

    public HabitListAdapter(ArrayList<Habit> habits) {
        this.habitsList = habits;
    }

    @NonNull
    @Override
    public HabitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_habit, parent, false);

        HabitListAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HabitListAdapter.ViewHolder holder, final int position) {
        Habit habit = habitsList.get(position);

        ((TextView) holder.mConstraintLayout.findViewById(R.id.ticket_habit_name)).setText(habit.getTitle());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(habit.getStartDate());

        ((TextView) holder.mConstraintLayout.findViewById(R.id.ticket_habit_date)).setText(date);

        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open Single Habit Fragment

            }
        });

    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

}
