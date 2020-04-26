package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.TaskHolder;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddJobDialog extends DialogFragment {

    private IAddJobDialog iDialog;
    private FragmentManager fm;

    public void createDialog(FragmentManager fm, IAddJobDialog iDialog) {
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

        final View rootView = inflater.inflate(R.layout.dialog_add_job, null);
       rootView.findViewById(R.id.add_job_task_add_button).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final LinearLayout layout = rootView.findViewById(R.id.add_job_task_list);
               final JobTaskItem item = new JobTaskItem(layout.getContext());
               layout.addView(item.view);
               item.taskDeleteButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       layout.removeView(item.view);
                   }
               });
           }
       });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Add Job", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String jobName = ((EditText) rootView.findViewById(R.id.add_job_name)).getText().toString();
                        String jobDescription = ((EditText) rootView.findViewById(R.id.add_job_description)).getText().toString();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date jobDeadline = new Date();
                        try {
                            jobDeadline = df.parse(((EditText) rootView.findViewById(R.id.add_job_deadline)).getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ArrayList<TaskHolder> tasks = new ArrayList<>();
                        LinearLayout list = rootView.findViewById(R.id.add_job_task_list);
                        for(int i = 0 ; i < list.getChildCount() ; i ++){
                            String taskName = ((EditText)list.getChildAt(i).findViewById(R.id.add_job_task_name)).getText().toString();
                            Date taskDeadline = new Date();
                            try {
                                 taskDeadline = df.parse(((EditText)list.getChildAt(i).findViewById(R.id.add_job_task_date)).getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            TaskHolder task = new TaskHolder(taskName, taskDeadline);
                            tasks.add(task);
                        }
                        iDialog.onPositiveClicked(jobName, jobDescription, jobDeadline, tasks);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddJobDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}