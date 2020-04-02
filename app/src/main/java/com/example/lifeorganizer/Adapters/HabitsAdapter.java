package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.R;

import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder> {

    private Context mCtx;
    private List<Habit> habits;

    public HabitsAdapter(Context mCtx, List<Habit> taskList) {
        this.mCtx = mCtx;
        this.habits = habits;
    }

    @Override
    public HabitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new HabitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HabitsViewHolder holder, int position) {
        Habit h = habits.get(position);
        holder.textViewTitle.setText(h.getTitle());
        holder.textViewDesc.setText(h.getDescription());

        //if (t.isFinished())
        //    holder.textViewStatus.setText("Completed");
        //else
            holder.textViewStatus.setText("Not Completed");
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    class HabitsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTitle, textViewDesc;

        public HabitsViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Habit habit = habits.get(getAdapterPosition());

            Toast.makeText(mCtx, habit.getTitle(), Toast.LENGTH_SHORT).show();

            //Intent intent = new Intent(mCtx, UpdateHabitActivity.class);
            //intent.putExtra("habit", habit);

            //mCtx.startActivity(intent);
        }
    }
}