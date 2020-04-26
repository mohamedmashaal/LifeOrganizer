package com.example.lifeorganizer.dialogs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lifeorganizer.R;

import java.util.Date;

public class JobTaskItem {
    public Context context;
    public EditText taskNameEditText;
    public EditText taskDeadlineEditText;
    public Date taskDeadline;
    public Button taskDeleteButton;
    public View view;
    public int index;

    public JobTaskItem(Context context){
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflator= LayoutInflater.from(context);
        view = inflator.inflate(R.layout.job_task_item, null);
        taskNameEditText = view.findViewById(R.id.add_job_task_name);
        taskDeadlineEditText = view.findViewById(R.id.add_job_task_date);
        taskDeleteButton = view.findViewById(R.id.add_job_task_delete);
    }

}
