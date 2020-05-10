package com.example.lifeorganizer.dialogs;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditHabitDialog extends DialogFragment {

    private IEditHabitDialog iDialog;
    private FragmentManager fm;
    private Habit habit;

    public void createDialog(FragmentManager fm, IEditHabitDialog iDialog, Habit habit) {
        this.fm = fm;
        this.iDialog = iDialog;
        this.habit = habit;
    }

    public void showDialog() {
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_add_habit, null);

        TextView header = rootView.findViewById(R.id.add_habit_header);
        header.setText("Edit Habit");

        rootView.findViewById(R.id.add_habit_days).setVisibility(View.GONE);
        rootView.findViewById(R.id.add_habit_days_title).setVisibility(View.GONE);
        rootView.findViewById(R.id.add_habit_date_picker).setVisibility(View.GONE);
        rootView.findViewById(R.id.add_habit_date_title).setVisibility(View.GONE);

        ((EditText) rootView.findViewById(R.id.add_habit_name)).setText(habit.getTitle());
        ((EditText) rootView.findViewById(R.id.add_habit_description)).setText(habit.getDescription());
        ((EditText) rootView.findViewById(R.id.add_habit_hours)).setText(habit.getHrsPerWeek()+"");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String habitName = ((EditText) rootView.findViewById(R.id.add_habit_name)).getText().toString();
                        String habitDescription = ((EditText) rootView.findViewById(R.id.add_habit_description)).getText().toString();

                        String hrs = ((EditText) rootView.findViewById(R.id.add_habit_hours)).getText().toString();
                        int habitHours = (!hrs.equals("")) ? Integer.valueOf(hrs) : 0;

                        habit.setTitle(habitName);
                        habit.setHrsPerWeek(habitHours);
                        habit.setDescription(habitDescription);
                        iDialog.onPositiveClicked(habit);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditHabitDialog.this.getDialog().cancel();
                    }
                });
        final AlertDialog dialog1 = builder.create();
        dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = (Button) dialog1.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String habitName = ((EditText) rootView.findViewById(R.id.add_habit_name)).getText().toString();
                        String habitDescription = ((EditText) rootView.findViewById(R.id.add_habit_description)).getText().toString();

                        String hrs = ((EditText) rootView.findViewById(R.id.add_habit_hours)).getText().toString();
                        int habitHours = (!hrs.equals("")) ? Integer.valueOf(hrs) : 0;

                        boolean isError = false;
                        if(habitName.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_habit_name)).setError("Enter habit name");
                        }
                        if(hrs.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_habit_hours)).setError("Enter habit hrs");
                        }

                        if(!isError){
                            dialog1.dismiss();
                            habit.setTitle(habitName);
                            habit.setHrsPerWeek(habitHours);
                            habit.setDescription(habitDescription);
                            iDialog.onPositiveClicked(habit);
                        }
                    }
                });
            }
        });
        return dialog1;
    }
}