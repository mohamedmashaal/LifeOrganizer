package com.example.lifeorganizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddHabitDialog;
import com.example.lifeorganizer.dialogs.AddTaskDialog;
import com.example.lifeorganizer.dialogs.IAddHabitDialog;

import java.util.Date;

public class FragmentHabit extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_habit, container, false);



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final TextView textView = (TextView) view.findViewById(R.id.testDialog);

        ((FloatingActionButton) view.findViewById(R.id.fb_add_habit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHabitDialog mainDialog = new AddHabitDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddHabitDialog() {

                    @Override
                    public void onPositiveClicked(String title, String description, String daysMask, int hrsPerWeek, Date startDate) {
                        textView.setText(daysMask);
                        //TODO call add nwe task here
                    }
                });
                mainDialog.showDialog();
            }
        });


    }

}
