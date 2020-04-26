package com.example.lifeorganizer.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.JobFragment.OnListFragmentInteractionListener;
import com.example.lifeorganizer.fragments.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyJobRecyclerViewAdapter extends RecyclerView.Adapter<MyJobRecyclerViewAdapter.ViewHolder> {

    private final List<Job> mValues;
    private final HashMap<Job, ArrayList<Task>> mJobTasks;
    private final OnListFragmentInteractionListener mListener;

    public MyJobRecyclerViewAdapter(List<Job> items, HashMap<Job, ArrayList<Task>> tasks, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mJobTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle());
        holder.mContentView.setText(getProgress(mJobTasks.get(holder.mItem)));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValues.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private String getProgress(ArrayList<Task> tasks) {
        int count = 0;
        if(tasks != null) {
            for (Task task : tasks) {
                if (task.isFinished())
                    count++;
            }
            return count + "/" + tasks.size();
        }
        return count + "/" + count;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button mDeleteButton;
        public Job mItem;
        public ArrayList<Task> mTasks;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.job_item_title);
            mContentView = (TextView) view.findViewById(R.id.job_item_progress);
            mDeleteButton = (Button) view.findViewById(R.id.job_item_delete_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
