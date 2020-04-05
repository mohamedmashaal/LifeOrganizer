package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.lifeorganizer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHabitDialog extends DialogFragment {

    private IAddHabitDialog iDialog;
    private FragmentManager fm;

    public void createDialog(FragmentManager fm, IAddHabitDialog iDialog) {
        this.fm = fm;
        this.iDialog = iDialog;
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

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String habitName = ((EditText) rootView.findViewById(R.id.add_habit_name)).getText().toString();
                        String habitDescription = ((EditText) rootView.findViewById(R.id.add_habit_description)).getText().toString();

                        String hrs = ((EditText) rootView.findViewById(R.id.add_habit_hours)).getText().toString();
                        int habitHours = (!hrs.equals("")) ? Integer.valueOf(hrs) : 0;
                        String daysMask = getDaysMask(rootView);

                        DatePicker datePicker = rootView.findViewById(R.id.add_habit_date_picker);
                        Date habitDate = getDate(datePicker);

                        iDialog.onPositiveClicked(habitName, habitDescription, daysMask, habitHours, habitDate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddHabitDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private String getDaysMask(View rootView) {
        Boolean[] days = {
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_st)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_su)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_mo)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_tu)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_we)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_th)).isChecked(),
                ((CheckBox) rootView.findViewById(R.id.add_habit_day_fr)).isChecked(),
        };

        StringBuilder stringBuilder = new StringBuilder(7);
        for (boolean day: days) {
            stringBuilder.append(day ? 1 : 0);
        }

        return stringBuilder.toString();
    }

    private Date getDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
