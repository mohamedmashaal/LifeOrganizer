package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTaskDialog extends DialogFragment {

    private IEditTaskDialog iDialog;
    private FragmentManager fm;
    private Task task;

    public void createDialog(FragmentManager fm, IEditTaskDialog iDialog, Task task){
        this.fm = fm;
        this.iDialog = iDialog;
        this.task = task;
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
        TextView header = rootView.findViewById(R.id.add_task_header);
        header.setText("Edit Task");
        EditText titleText = rootView.findViewById(R.id.add_task_title);
        titleText.setText(task.getTitle());

        DatePicker datePicker = rootView.findViewById(R.id.add_task_date_picker);
        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getDate());
        datePicker.init(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Edit Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String taskTitle = ((EditText) rootView.findViewById(R.id.add_task_title)).getText().toString();

                        DatePicker datePicker = rootView.findViewById(R.id.add_task_date_picker);
                        Date taskDate = getDate(datePicker);

                        task.setDate(taskDate);
                        task.setTitle(taskTitle);
                        iDialog.onPositiveClicked(task);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditTaskDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
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
