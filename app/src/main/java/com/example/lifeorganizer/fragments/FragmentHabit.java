package com.example.lifeorganizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifeorganizer.Adapters.HabitListAdapter;
import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddHabitDialog;
import com.example.lifeorganizer.dialogs.IAddHabitDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentHabit extends Fragment {

    private ArrayList<Habit> habitList;
    private RecyclerView mRecyclerView;
    private HabitListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HabitManager habitManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_habit, container, false);

        habitManager = HabitManager.getInstance(getContext());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.habits_list_recycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        habitManager.getHabits(new AfterGetHabits() {
            @Override
            public void afterGetHabits(List<Habit> habits) {
                habitList = new ArrayList<>(habits);
            }
        });

        mAdapter = new HabitListAdapter(habitList);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((FloatingActionButton) view.findViewById(R.id.fb_add_habit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHabitDialog mainDialog = new AddHabitDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddHabitDialog() {

                    @Override
                    public void onPositiveClicked(String title, String description, String daysMask, int hrsPerWeek, Date startDate) {
                        //TODO call add nwe task here
                    }
                });
                mainDialog.showDialog();
            }
        });


    }

}
