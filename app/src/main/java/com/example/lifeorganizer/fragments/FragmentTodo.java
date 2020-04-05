package com.example.lifeorganizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.IAddTaskDialog;
import com.example.lifeorganizer.dialogs.AddTaskDialog;

import java.util.Date;

public class FragmentTodo extends Fragment {

    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final TextView textView = (TextView) view.findViewById(R.id.testDialog);

        ((FloatingActionButton) view.findViewById(R.id.fb_add_task)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog mainDialog = new AddTaskDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddTaskDialog() {

                    @Override
                    public void onPositiveClicked(String title, Date date, boolean finished, int timeSpentInSeconds) {
                        textView.setText(date.toString());
                        //TODO call add new task here
                    }
                });
                mainDialog.showDialog();
            }
        });


    }
}
