package com.example.lifeorganizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Adapters.TaskAdapter;
import com.example.lifeorganizer.Backend.AfterCreateTask;
import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.EditTaskDialog;
import com.example.lifeorganizer.dialogs.IAddTaskDialog;
import com.example.lifeorganizer.dialogs.AddTaskDialog;
import com.example.lifeorganizer.dialogs.IEditTaskDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentTodo extends Fragment {

    private FragmentActivity myContext;
    ImageView prevBtn;
    ImageView nextBtn;
    TextView dateText;
    Date date = new Date();
    final String [] MONTHS = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep", "Oct","Nov","Dec"};
    List<Task> todoTasks;
    List<Task> habitTasks;
    List<Task> jobTasks;
    public enum  TASKS_TYPE {Habit,TODO,Job};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((FloatingActionButton) view.findViewById(R.id.fb_add_task)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog mainDialog = new AddTaskDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddTaskDialog() {
                    @Override
                    public void onPositiveClicked(String title, final Date date) {

                        //textView.setText(date);
                        Task task = new Task(title, date,false,0);

                        TaskManager.getInstance(getActivity()).createTask(task, new AfterCreateTask() {
                            @Override
                            public void afterCreateTask() {
                                loadTheList();
                            }
                        });
                    }
                });
                mainDialog.showDialog();
            }
        });

        dateText = view.findViewById(R.id.todoDayText);
        prevBtn = view.findViewById(R.id.todoPrev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
                setDateText();
                loadTheList();
            }
        });
        nextBtn = view.findViewById(R.id.todoNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
                setDateText();
                loadTheList();
            }
        });

        setDateText();
        loadTheList();
    }
    private void changeDate(int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        date = calendar.getTime();
    }
    private void setDateText(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String d = calendar.get(Calendar.DAY_OF_MONTH)+" ";
        d += MONTHS[calendar.get(Calendar.MONTH)] + " ";
        d += calendar.get(Calendar.YEAR);
        dateText.setText(d);
    }
    private void loadTheList(){

        TaskManager.getInstance(getContext()).getTasks(date, new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                //TODO filter them to habits and tasks
                todoTasks = new ArrayList<>();
                habitTasks = new ArrayList<>();
                jobTasks = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if(task.isHabitTask()){
                        habitTasks.add(tasks.get(i));
                    } else if(task.isJobTask()){
                        jobTasks.add(task);
                    }else{
                        todoTasks.add(task);
                    }
                }
                //Test Code
                habitTasks.add(new Task("habit Task",new Date(),false,0));

                TaskAdapter todoAdapter = new TaskAdapter(getActivity(), todoTasks,TASKS_TYPE.TODO,FragmentTodo.this);
                TaskAdapter habitAdapter = new TaskAdapter(getActivity(),habitTasks, TASKS_TYPE.Habit,FragmentTodo.this);
                TaskAdapter jobAdapter =  new TaskAdapter(getActivity(),jobTasks, TASKS_TYPE.Job,FragmentTodo.this);

                // to do list
                final ListView todoListView = (ListView) getView().findViewById(R.id.todoListView);
                todoListView.setAdapter(todoAdapter);


                // habit list
                ListView habitListView = (ListView) getView().findViewById(R.id.habitTasksListView);
                habitListView.setAdapter(habitAdapter);


                //TODO add job tasks list

            }
        });
        //TODO Load events
    }

    public void notifyChange(){
        loadTheList();
    }

}
