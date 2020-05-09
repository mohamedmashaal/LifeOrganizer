package com.example.lifeorganizer.fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifeorganizer.Adapters.HabitListAdapter;
import com.example.lifeorganizer.Backend.AfterEditHabit;
import com.example.lifeorganizer.Backend.AfterEditTask;
import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.EditHabitDialog;
import com.example.lifeorganizer.dialogs.EditTaskDialog;
import com.example.lifeorganizer.dialogs.IEditHabitDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.view.Gravity;
import android.support.v7.widget.GridLayout;
import android.widget.Toast;

public class FragmentHabitView extends Fragment{
    ImageView prev, next;
    TextView calenderMonth;
    GridLayout calender;
    EditText nameField , descriptionFiled, hrsFiled, startDateFiled;
    CheckBox [] daysCheckBox = new CheckBox[7];
    Button editBtn;
    Date currentMonth = new Date();
    final String [] MONTHS = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep", "Oct","Nov","Dec"};
    private static HabitListAdapter habitListAdapter;
    private static Habit habit;
    public static FragmentHabitView newInstance(Habit habit, HabitListAdapter habitListAdapter) {
        FragmentHabitView.habit = habit;
        FragmentHabitView.habitListAdapter = habitListAdapter;
        FragmentHabitView fragment = new FragmentHabitView();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.habit_view_fragment, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main);
        prev = view.findViewById(R.id.habitCalenderPrev);
        next = view.findViewById(R.id.habitCalenderNext);
        calenderMonth = view.findViewById(R.id.habitCalenderMonth);
        calender = view.findViewById(R.id.habitCalender);
        nameField = view.findViewById(R.id.habitNameField);
        descriptionFiled = view.findViewById(R.id.habitDescriptionField);
        hrsFiled = view.findViewById(R.id.habitHoursField);
        startDateFiled = view.findViewById(R.id.habitDateField);
        daysCheckBox[0] = view.findViewById(R.id.habitSaCheckbox);
        daysCheckBox[1] = view.findViewById(R.id.habitSuCheckbox);
        daysCheckBox[2] = view.findViewById(R.id.habitMoCheckbox);
        daysCheckBox[3] = view.findViewById(R.id.habitTuCheckbox);
        daysCheckBox[4] = view.findViewById(R.id.habitWeCheckbox);
        daysCheckBox[5] = view.findViewById(R.id.habitThCheckbox);
        daysCheckBox[6] = view.findViewById(R.id.habitFrCheckbox);
        editBtn = view.findViewById(R.id.habit_edit_btn);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
                setDateText();
                updateCalendar();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
                setDateText();
                updateCalendar();
            }
        });

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                TextView t = new TextView(getActivity());
                t.setPadding(5,4,5,4);
                t.setText(i +","+j);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.rowSpec = GridLayout.spec(i+1);
                float colWeight = 1;
                //layoutParams.setMargins(5,4,5,4);
                layoutParams.columnSpec  = GridLayout.spec(j,colWeight);
                layoutParams.setGravity(Gravity.CENTER_HORIZONTAL);
                calender.addView(t, layoutParams);
            }
        }

        updateDetails();
        setDateText();
        updateCalendar();
        setEditListener();
    }
    private void changeDate(int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentMonth);
        calendar.add(Calendar.MONTH, amount);
        currentMonth = calendar.getTime();
    }
    private void setDateText(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentMonth);
        String d = MONTHS[calendar.get(Calendar.MONTH)] + " ";
        d += calendar.get(Calendar.YEAR);
        calenderMonth.setText(d);
    }
    private void setEditListener(){
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditHabitDialog editDialog = new EditHabitDialog();
                editDialog.createDialog((getActivity()).getSupportFragmentManager(), new IEditHabitDialog() {
                    @Override
                    public void onPositiveClicked(Habit habit) {
                        HabitManager taskManager = HabitManager.getInstance(getActivity());
                        taskManager.editHabit(habit, new AfterEditHabit() {
                            @Override
                            public void afterEditHabit() {
                                updateDetails();
                                habitListAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }, habit);
                editDialog.showDialog();
            }
        });
    }
    private void updateCalendar(){
        setFirstDateOfMonth();
        final int weekDay = getDayInWeek(currentMonth);
        final int positionOfFirstDay = weekDay + 7;
        final int lastDay = getLastDayInMonth();
        //start from  seven because the first 7 elements in gridlayout are the days name
        for (int i = 7; i < 49; i++) {
            TextView textView = (TextView)calender.getChildAt(i);
            textView.setBackground(null);
            textView.setTypeface(null, Typeface.NORMAL);
            textView.setTextColor(((TextView)calender.getChildAt(0)).getTextColors());
            //textView.setTextSize(15);
            textView.setTextSize(13);
            if(i < positionOfFirstDay || i >= positionOfFirstDay +lastDay){
                textView.setText(" ");
            } else {
                textView.setText((i-positionOfFirstDay+1)+"");
            }
        }

        //TODO merge the backend here
        /*ArrayList<Task> tasksList = getTasks();
        for (int j = 0; j < tasksList.size(); j++) {
            Task t = tasksList.get(j);
            int taskDay = getDayInMonth(t.getDate());
            TextView textView = (TextView)calender.getChildAt(taskDay -1 +positionOfFirstDay );
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
            //textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(16);
            if((new Date()).compareTo(t.getDate()) == 1) {
                if (t.isFinished()) {
                    //textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorDone));
                    textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_green_24dp));
                } else {
                    //textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorMissed));
                    textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_wrong_red_24dp));
                }
            }
        }
        */
        final TaskManager taskManager = TaskManager.getInstance(getActivity());
        Calendar c = Calendar.getInstance();
        c.setTime(currentMonth);

        taskManager.getTasks(c.get(Calendar.MONTH), c.get(Calendar.YEAR), new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                ArrayList<Task> tasksList = new ArrayList<>(tasks);
                Toast.makeText(getActivity(),tasksList.size()+"",Toast.LENGTH_SHORT).show();
                int [] state = new int[lastDay];
                String days = habit.getDaysMask();
                for (int i = 0; i < state.length; i++) {
                    if(days.charAt((i+weekDay)%7) == '1'){
                        state[i] = 0;
                    } else {
                        state[i] = -1;
                    }
                }
                for (int j = 0; j < tasksList.size(); j++) {
                    Task t = tasksList.get(j);
                    if(t.isFinished()){
                        int taskDay = getDayInMonth(t.getDate());
                        state[taskDay-1] = 1;
                    }
                }
                for (int i = 0; i < state.length; i++) {
                    if(state[i] != -1) {
                        TextView textView = (TextView) calender.getChildAt(i + positionOfFirstDay);
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                        textView.setTextSize(16);
                        if (state[i] == 0) {
                            textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_wrong_red_24dp));
                        } else if (state[i] == 1) {
                            textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_green_24dp));
                        }
                    }
                }
            }
        });



    }
    private void updateDetails(){
        nameField.setText(habit.getTitle());
        descriptionFiled.setText(habit.getDescription());
        hrsFiled.setText(habit.getHrsPerWeek()+"");
        Date date = habit.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String d = calendar.get(Calendar.DAY_OF_MONTH)+" ";
        d += MONTHS[calendar.get(Calendar.MONTH)] + " ";
        d += calendar.get(Calendar.YEAR);
        startDateFiled.setText(d);
        String days = habit.getDaysMask();
        for (int i = 0; i < days.length(); i++) {
            if(days.charAt(i) == '1'){
                daysCheckBox[i].setChecked(true);
            } else {
                daysCheckBox[i].setChecked(false);
            }
        }
    }
    private void setFirstDateOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentMonth);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        currentMonth = cal.getTime();
    }
    private int getDayInWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) % 7;
    }
    private int getDayInMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private int getLastDayInMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentMonth);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    private ArrayList<Task> getTasks(){
        ArrayList<Task> list = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(currentMonth);
        for (int i = 0; i < 25; i++) {
            Task t =new Task("t",c.getTime(),i%2 == 1,25);
            c.add(Calendar.DAY_OF_MONTH, 1);
            list.add(t);
        }

        return list;
    }

}




