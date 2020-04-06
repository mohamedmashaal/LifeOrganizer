package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lifeorganizer.Backend.AfterDeleteHabit;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentHabitView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.ViewHolder> {

    private ArrayList<Habit> habitsList = new ArrayList<>();
    private Context context;
    private HabitListAdapter habitListAdapter;
    private FragmentManager fragmentManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ConstraintLayout mConstraintLayout;

        public ViewHolder(ConstraintLayout v) {
            super(v);
            mConstraintLayout = v;
        }
    }

    public HabitListAdapter(ArrayList<Habit> habits, Context context, FragmentManager fragmentManager) {
        this.habitsList = habits;
        this.context = context;
        this.habitListAdapter = this;
        this.fragmentManager = fragmentManager;
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
        final Habit habit = habitsList.get(position);

        ((TextView) holder.mConstraintLayout.findViewById(R.id.ticket_habit_name)).setText(habit.getTitle());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(habit.getStartDate());

        ((TextView) holder.mConstraintLayout.findViewById(R.id.ticket_habit_date)).setText(date);

        ((Button) holder.mConstraintLayout.findViewById(R.id.ticket_habit_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Are you sure you want to delete this habit?")
                        .setTitle("Delete Habit");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HabitManager.getInstance(context).deleteHabit(habit, new AfterDeleteHabit() {
                            @Override
                            public void afterDeleteHabit() {
                                habitsList.remove(habit);
                                habitListAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open Single Habit Fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container,
                        FragmentHabitView.newInstance(habit));
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }



}
