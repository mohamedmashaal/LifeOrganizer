package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Backend.AfterDeleteTask;
import com.example.lifeorganizer.Backend.AfterEditTask;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.EditTaskDialog;
import com.example.lifeorganizer.dialogs.IEditTaskDialog;
import com.example.lifeorganizer.fragments.FragmentTodo;

import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    List<Task> tasksList;
    TaskAdapter taskAdapter;
    FragmentTodo.TASKS_TYPE tasksType;
    Context context;
    FragmentTodo todoFragment;
    final String [] MONTHS = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep", "Oct","Nov","Dec"};

    public TaskAdapter(Context context, List<Task> tasks, FragmentTodo.TASKS_TYPE tasksType,FragmentTodo fragment) {
        super(context, 0, tasks);
        tasksList = tasks;
        taskAdapter = this;
        this.tasksType = tasksType;
        this.context = context;
        this.todoFragment = fragment;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
        }

        final Task task = getItem(position);

        final TextView nameText = (TextView) listItemView.findViewById(R.id.task_name_text);
        nameText.setText(task.getTitle());
        setNameTextListener(nameText,task);

        final EditText timeField = listItemView.findViewById(R.id.task_time_spent_field);
        timeField.setText(task.getTimeSpentInSeconds()/60+"");
        if(task.isFinished()){
            timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            //timeField.requestFocus();
        } else {
            timeField.setInputType(InputType.TYPE_NULL);
        }
        //TODO put change listener
        //timeField.requestFocus();
        //timeField.setOnC

        final CheckBox checkBox = listItemView.findViewById(R.id.task_checkbox);
        checkBox.setChecked(task.isFinished());
        setCheckBoxListener(checkBox,task,timeField);


        final Button deleteBtn = (Button) listItemView.findViewById(R.id.task_delete_btn);
        setDeleteListener(deleteBtn,task);
        if(tasksType != FragmentTodo.TASKS_TYPE.TODO){
            deleteBtn.setVisibility(View.GONE);
        }

        return listItemView;
    }

    private void setCheckBoxListener(final CheckBox checkBox, final Task task, final EditText timeField){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final TaskManager taskManager = TaskManager.getInstance(context);
                task.setFinished(isChecked);
                final boolean state = isChecked;
                //Toast.makeText(getContext(), currentWord.name+ "," + currentWord.time, Toast.LENGTH_SHORT).show();
                //TODO did set reflect in database?
                if(isChecked) {
                    timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    task.setFinished(state);
                    //timeField.requestFocus();
                    taskManager.editTask(task, new AfterEditTask() {
                        @Override
                        public void afterEditTask() {
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to un mark the task?").setTitle("Unmark Task");
                    builder.setPositiveButton("Unmark", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            timeField.setInputType(InputType.TYPE_NULL);
                            timeField.setText("0");
                            task.setTimeSpentInSeconds(0);
                            task.setFinished(state);
                            taskManager.editTask(task, new AfterEditTask() {
                                @Override
                                public void afterEditTask() {
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            checkBox.setChecked(true);
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void setNameTextListener(final TextView nameText, final Task task){
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tasksType == FragmentTodo.TASKS_TYPE.TODO){
                    EditTaskDialog editDialog = new EditTaskDialog();
                    editDialog.createDialog(((AppCompatActivity)context).getSupportFragmentManager(), new IEditTaskDialog() {
                        @Override
                        public void onPositiveClicked(Task task) {
                            TaskManager taskManager = TaskManager.getInstance(context);
                            taskManager.editTask(task, new AfterEditTask() {
                                @Override
                                public void afterEditTask() {
                                }
                            });
                            todoFragment.notifyChange();
                        }
                    }, task);
                    editDialog.showDialog();
                } else if(tasksType == FragmentTodo.TASKS_TYPE.Habit){
                    //TODO navigate the tasks from habits to the source of the task
                    String temp2 = task.getTitle();
                    Toast.makeText(context,temp2,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDeleteListener(final Button deleteBtn, final Task task){
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
    }
}
