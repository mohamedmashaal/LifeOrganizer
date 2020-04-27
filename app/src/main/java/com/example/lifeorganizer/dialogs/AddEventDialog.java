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

public class AddEventDialog extends DialogFragment {

    private IAddEventDialog iDialog;
    private FragmentManager fm;
    private String positiveBtn;

    public void createDialog(FragmentManager fm, IAddEventDialog iDialog, String positiveBtn) {
        this.fm = fm;
        this.iDialog = iDialog;
        this.positiveBtn = positiveBtn;
    }

    public void showDialog() {
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_add_event, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String eventName = ((EditText) rootView.findViewById(R.id.add_event_name)).getText().toString();
                        String eventDescription = ((EditText) rootView.findViewById(R.id.add_event_description)).getText().toString();

                        String startHrs = ((EditText) rootView.findViewById(R.id.add_event_start_hours)).getText().toString();
                        int eventSHrs = (!startHrs.equals("")) ? Integer.valueOf(startHrs) : 0;

                        String startMin = ((EditText) rootView.findViewById(R.id.add_event_min)).getText().toString();
                        int eventSMin = (!startMin.equals("")) ? Integer.valueOf(startMin) : 0;

                        String duration = ((EditText) rootView.findViewById(R.id.add_event_duration)).getText().toString();
                        int eventDuration = (!duration.equals("")) ? Integer.valueOf(duration) : 0;


                        DatePicker datePicker = rootView.findViewById(R.id.add_event_date_picker);
                        Date eventDate = getDate(datePicker);

                        iDialog.onPositiveClicked(eventName, eventDescription, eventSHrs, eventSMin, eventDuration, eventDate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddEventDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
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
