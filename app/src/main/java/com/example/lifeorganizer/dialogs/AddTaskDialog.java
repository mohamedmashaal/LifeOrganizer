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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifeorganizer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskDialog extends DialogFragment {

    private IAddTaskDialog iDialog;
    private FragmentManager fm;


    public void createDialog(FragmentManager fm, IAddTaskDialog iDialog){
        this.fm = fm;
        this.iDialog = iDialog;
    }

    public void showDialog(){
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_add_task, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Add Task", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddTaskDialog.this.getDialog().cancel();
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
                        String taskTitle = ((EditText) rootView.findViewById(R.id.add_task_title)).getText().toString();

                        DatePicker datePicker = rootView.findViewById(R.id.add_task_date_picker);
                        Date taskDate = getDate(datePicker);

                        if(taskTitle.length() == 0){
                            ((EditText)rootView.findViewById(R.id.add_task_title)).setError("Enter Task Title");
                        }else {
                            dialog1.dismiss();
                            iDialog.onPositiveClicked(taskTitle, taskDate);
                        }
                    }
                });
            }
        });
        return dialog1;
    }

    private Date getDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return calendar.getTime();
    }

}
