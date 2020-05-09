package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HabitStatisticsDialog extends DialogFragment {

    private FragmentManager fm;
    private PieChart pieChart;
    private EditText monthEditText;
    private EditText yearEditText;
    private TaskManager taskManager;

    public void createDialog(FragmentManager fm) {
        this.fm = fm;
        taskManager = TaskManager.getInstance(getContext());
    }

    public void showDialog() {
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_habit_statistics, null);
        monthEditText = rootView.findViewById(R.id.habit_stats_month_edit_text);
        yearEditText = rootView.findViewById(R.id.habit_stats_year_edit_text);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        pieChart = rootView.findViewById(R.id.habit_stats_piechart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        Date date = java.util.Calendar.getInstance().getTime();
        populateEntries(date);

        builder.setView(rootView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HabitStatisticsDialog.this.getDialog().cancel();
                    }
                });
        rootView.findViewById(R.id.habit_stats_get_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month = Integer.parseInt(monthEditText.getText().toString());
                int year = Integer.parseInt(yearEditText.getText().toString());
                populateEntries(month, year);
            }
        });
        return builder.create();
    }

    private void populateEntries(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        taskManager.getTasksForAllHabitsAndMonth(month, year, new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                ArrayList<PieEntry> entries = new ArrayList<>();
                HashMap<String, Integer> totalTime = new HashMap<>();
                for(int i = 0 ; i < tasks.size() ; i ++){
                    String title = tasks.get(i).getTitle();
                    if(totalTime.containsKey(title)){
                        totalTime.put(title, totalTime.get(title) + tasks.get(i).getTimeSpentInSeconds()/3600);
                    }
                    else{
                        totalTime.put(title, tasks.get(i).getTimeSpentInSeconds()/3600);
                    }
                }
                for(HashMap.Entry<String, Integer> entry: totalTime.entrySet()){
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                }
                PieDataSet dataSet = new PieDataSet(entries, null);

                dataSet.setValueTextSize(8f);
                dataSet.setColors(getColors());

                PieData data = new PieData(dataSet);
                pieChart.setData(data);
                pieChart.invalidate();
            }
        });

    }

    private void populateEntries(int m, int y) {
        int month = m;
        int year = y;
        taskManager.getTasksForAllHabitsAndMonth(month, year, new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                ArrayList<PieEntry> entries = new ArrayList<>();
                HashMap<String, Integer> totalTime = new HashMap<>();
                for(int i = 0 ; i < tasks.size() ; i ++){
                    String title = tasks.get(i).getTitle();
                    if(totalTime.containsKey(title)){
                        totalTime.put(title, totalTime.get(title) + tasks.get(i).getTimeSpentInSeconds()/3600);
                    }
                    else{
                        totalTime.put(title, tasks.get(i).getTimeSpentInSeconds()/3600);
                    }
                }
                for(HashMap.Entry<String, Integer> entry: totalTime.entrySet()){
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                }
                PieDataSet dataSet = new PieDataSet(entries, null);

                dataSet.setValueTextSize(8f);
                dataSet.setColors(getColors());

                PieData data = new PieData(dataSet);
                pieChart.setData(data);
                pieChart.invalidate();
            }
        });

    }

    private ArrayList<Integer> getColors(){
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
}
