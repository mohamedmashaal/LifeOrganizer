package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Backend.AfterDeleteTask;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.FragmentTodo;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    List<Task> tasksList;
    TaskAdapter taskAdapter;
    FragmentTodo.TASKS_TYPE tasksType;
    public TaskAdapter(Context context, List<Task> tasks, FragmentTodo.TASKS_TYPE tasksType) {
        super(context, 0, tasks);
        tasksList = tasks;
        taskAdapter = this;
        this.tasksType = tasksType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
        }



        final Task task = getItem(position);
        final TextView nameText = (TextView) listItemView.findViewById(R.id.task_name_text);
        nameText.setText(task.getTitle());

        final EditText timeField = listItemView.findViewById(R.id.task_time_spent_field);
        timeField.setText(task.getTimeSpentInSeconds()/60+"");
        if(task.isFinished()){
            timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            timeField.setInputType(InputType.TYPE_NULL);
        }
        //TODO put change listener
        //timeField.requestFocus();

        final CheckBox checkBox = listItemView.findViewById(R.id.task_checkbox);
        checkBox.setChecked(task.isFinished());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO warning the user from uncheck
                task.setFinished(isChecked);

                //Toast.makeText(getContext(), currentWord.name+ "," + currentWord.time, Toast.LENGTH_SHORT).show();
                //TODO did set reflect in database?
                if(isChecked) {
                   timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                } else {
                   timeField.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        final Button deleteBtn = (Button) listItemView.findViewById(R.id.task_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Are you sure you want to delete this the task?")
                        .setTitle("Delete Task");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TaskManager.getInstance(getContext()).deleteTask(task, new AfterDeleteTask() {
                            @Override
                            public void afterDeleteTask() {
                                tasksList.remove(task);
                                taskAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if(tasksType != FragmentTodo.TASKS_TYPE.TODO){
            deleteBtn.setVisibility(View.GONE);
        }
        // Find the TextView in the list_item.xml layout with the ID default_text_view.

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.*/
        return listItemView;
    }
}
