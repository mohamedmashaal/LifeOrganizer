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
                .setPositiveButton("Edit Task", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditTaskDialog.this.getDialog().cancel();
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
                            task.setDate(taskDate);
                            task.setTitle(taskTitle);
                            iDialog.onPositiveClicked(task);
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
