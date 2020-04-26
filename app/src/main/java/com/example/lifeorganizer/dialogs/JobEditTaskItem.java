package com.example.lifeorganizer.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.lifeorganizer.R;

import java.util.Date;

public class JobEditTaskItem {
    public Context context;
    public EditText taskNameEditText;
    public EditText taskDeadlineEditText;
    public Date taskDeadline;
    public Button taskDeleteButton;
    public CheckBox taskCheckBox;
    public View view;
    public int index;

    public JobEditTaskItem(Context context){
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflator= LayoutInflater.from(context);
        view = inflator.inflate(R.layout.job_task_item_edit, null);
        taskNameEditText = view.findViewById(R.id.edit_job_task_name);
        taskDeadlineEditText = view.findViewById(R.id.edit_job_task_date);
        taskDeleteButton = view.findViewById(R.id.edit_job_task_delete);
        taskCheckBox = view.findViewById(R.id.edit_job_task_done);
    }

}
