package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.FragmentTodo;

import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    List<Task> tasksList;
    TaskAdapter taskAdapter;
    FragmentTodo.TASKS_TYPE tasksType;
    Context context;
    FragmentTodo todoFragment;
    LayoutInflater mInflater;
    private long tStart;

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
        final ViewHolder holder;
        View listItemView = convertView;
        if (listItemView == null) {
            holder = new ViewHolder();
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //listItemView = mInflater.inflate(R.layout.task_item, parent,false);
            listItemView = mInflater.inflate(R.layout.task_item, null);
            holder.task = getItem(position);

            holder.timeFiled = (EditText) listItemView.findViewById(R.id.task_time_spent_field);

            holder.nameText = listItemView.findViewById(R.id.task_name_text);

            holder.checkBox = listItemView.findViewById(R.id.task_checkbox);

            holder.deleteBtn = (Button) listItemView.findViewById(R.id.task_delete_btn);

            holder.timerBtn = (Button) listItemView.findViewById(R.id.task_btn_timer);

            listItemView.setTag(holder);

        }else {
            holder = (ViewHolder) listItemView.getTag();
        }

        holder.timeFiled.setText(holder.task.getTimeSpentInSeconds()/60 + "");
        if(!holder.task.isFinished()){
            holder.timeFiled.setClickable(false);
            holder.timeFiled.setCursorVisible(false);
            holder.timeFiled.setFocusable(false);
        } else {
            holder.timeFiled.setClickable(true);
            holder.timeFiled.setCursorVisible(true);
            holder.timeFiled.setFocusable(true);
        }

        setTimeFiledListener(holder.timeFiled,holder.task);

        holder.nameText.setText(holder.task.getTitle());
        setNameTextListener(holder.nameText,holder.task);

        holder.checkBox.setChecked(holder.task.isFinished());
        setCheckBoxListener(holder.checkBox,holder.task,holder.timeFiled);

        setDeleteListener(holder.deleteBtn,holder.task);
        if(tasksType != FragmentTodo.TASKS_TYPE.TODO){
            holder.deleteBtn.setVisibility(View.GONE);
        }

        if(tasksType == FragmentTodo.TASKS_TYPE.Event){
            holder.timeFiled.setVisibility(View.GONE);
            listItemView.findViewById(R.id.minText).setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                if (btn.getText().equals("START")){
                    tStart = System.currentTimeMillis();
                    btn.setText("STOP");
                } else {
                    long tEnd = System.currentTimeMillis();
                    long tDelta = tEnd - tStart;
                    int elapsedSeconds = (int)(tDelta / 1000);
                    holder.timeFiled.setText(String.valueOf(elapsedSeconds));
                    btn.setText("START");
                }
            }
        });



        return listItemView;
    }

    private void setCheckBoxListener(final CheckBox checkBox, final Task task, final EditText timeField){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getContext(), task.isFinished()+ "," + isChecked, Toast.LENGTH_SHORT).show();
                /*if(task.isFinished() != isChecked){
                    Toast.makeText(context, "diff", Toast.LENGTH_SHORT).show();
                }*/
                final TaskManager taskManager = TaskManager.getInstance(context);
                task.setFinished(isChecked);
                final boolean state = isChecked;
                //Toast.makeText(getContext(), currentWord.name+ "," + currentWord.time, Toast.LENGTH_SHORT).show();
                //TODO did set reflect in database?
                if(isChecked) {
                    timeField.setClickable(true);
                    timeField.setCursorVisible(true);
                    timeField.setFocusable(true);
                    timeField.setFocusableInTouchMode(true);
                    task.setFinished(state);
                    //timeField.requestFocus();
                    taskManager.editTask(task, new AfterEditTask() {
                        @Override
                        public void afterEditTask() {
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to un mark the task?" + task.getTitle()).setTitle("Unmark Task");
                    builder.setPositiveButton("Unmark", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            timeField.setClickable(false);
                            timeField.setCursorVisible(false);
                            timeField.setFocusable(false);
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

    private void setTimeFiledListener(final EditText timeFiled,final Task task){
        timeFiled.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(timeFiled.getText().toString().length()>0){
                    task.setTimeSpentInSeconds(new Integer(timeFiled.getText().toString()) * 60);
                    TaskManager taskManager = TaskManager.getInstance(context);
                    taskManager.editTask(task, new AfterEditTask() {
                        @Override
                        public void afterEditTask() {
                        }
                    });
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}

class ViewHolder {
    EditText timeFiled;
    TextView nameText;
    CheckBox checkBox;
    Button deleteBtn;
    Button timerBtn;
    Task task;
}
