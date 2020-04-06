package com.example.lifeorganizer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Adapters.TaskAdapter;
import com.example.lifeorganizer.Backend.AfterCreateTask;
import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.IAddTaskDialog;
import com.example.lifeorganizer.dialogs.AddTaskDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentHabitView extends Fragment{
    ImageView prev;
    ImageView next;
    TextView calenderMonth;
    GridLayout calender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main);
        prev = view.findViewById(R.id.habitCalenderPrev);
        next = view.findViewById(R.id.habitCalenderNext);
        calenderMonth = view.findViewById(R.id.habitCalenderMonth);
        calender = view.findViewById(R.id.habitCalender);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                TextView t = new TextView(getActivity());
                t.setText(i +","+j);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.rowSpec = GridLayout.spec(i+1);
                float colWeight = 1;
                layoutParams.setMargins(0,25,0,0);
                layoutParams.columnSpec  =GridLayout.spec(j,colWeight);
                layoutParams.setGravity(Gravity.CENTER_HORIZONTAL);
                calender.addView(t, layoutParams);
            }
        }
    }
}




