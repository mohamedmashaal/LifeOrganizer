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

import com.example.lifeorganizer.Data.Event;
import com.example.lifeorganizer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventDialog extends DialogFragment {

    private IAddEventDialog iDialog;
    private FragmentManager fm;
    private String positiveBtn;
    private Event event;

    public void createDialog(FragmentManager fm, IAddEventDialog iDialog, String positiveBtn, Event event) {
        this.fm = fm;
        this.iDialog = iDialog;
        this.positiveBtn = positiveBtn;
        this.event = event;
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

        if(positiveBtn == "Edit Event"){
            ((TextView)rootView.findViewById(R.id.add_event_header)).setText("Edit Event");
            setFieldsData(rootView);
        }


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(positiveBtn, null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddEventDialog.this.getDialog().cancel();
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
                        boolean isError = false;
                        if(eventName.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_event_name)).setError("Enter event name");
                        }
                        if(startHrs.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_event_start_hours)).setError("Enter event time");
                        }
                        if(startMin.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_event_min)).setError("Enter event time");
                        }
                        if(duration.length() == 0){
                            isError = true;
                            ((EditText)rootView.findViewById(R.id.add_event_duration)).setError("Enter event duration");
                        }
                        if(!isError){
                            dialog1.dismiss();
                            iDialog.onPositiveClicked(eventName, eventDescription, eventSHrs, eventSMin, eventDuration, eventDate);
                        }
                    }
                });
            }
        });
        return dialog1;
    }

    private Date getDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
     private void setFieldsData(View view){
        ((TextView) view.findViewById(R.id.add_event_name)).setText(event.getTitle());
        ((TextView) view.findViewById(R.id.add_event_description)).setText(event.getDescription());
        ((TextView) view.findViewById(R.id.add_event_start_hours)).setText(String.valueOf(event.getStartHour()));
        ((TextView) view.findViewById(R.id.add_event_min)).setText(String.valueOf(event.getStartMinute()));
        ((TextView) view.findViewById(R.id.add_event_duration)).setText(String.valueOf(event.getDuration()));

        DatePicker datePicker = view.findViewById(R.id.add_event_date_picker);
        Calendar cal = Calendar.getInstance();
        cal.setTime(event.getStartDate());
        datePicker.init(
             cal.get(Calendar.YEAR),
             cal.get(Calendar.MONTH),
             cal.get(Calendar.DAY_OF_MONTH),
             null);
     }
}