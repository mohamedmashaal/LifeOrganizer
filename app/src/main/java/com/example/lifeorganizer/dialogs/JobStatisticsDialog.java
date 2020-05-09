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
import android.widget.LinearLayout;

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
import java.util.Date;
import java.util.HashMap;

public class JobStatisticsDialog extends DialogFragment {

    private FragmentManager fm;
    private PieChart pieChart;
    private ArrayList<Job> currentJobs;
    HashMap<Job, ArrayList<Task>> currentJobsTasks;
    private EditText fromEditText;
    private EditText toEditText;

    public void createDialog(FragmentManager fm, ArrayList<Job> currentJobs, HashMap<Job, ArrayList<Task>> currentJobsTasks) {
        this.fm = fm;
        this.currentJobs = currentJobs;
        this.currentJobsTasks = currentJobsTasks;
    }

    public void showDialog() {
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_job_statistics, null);
        fromEditText = rootView.findViewById(R.id.job_stats_from_edit_text);
        toEditText = rootView.findViewById(R.id.job_stats_to_edit_text);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        pieChart = rootView.findViewById(R.id.job_stats_piechart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        ArrayList<PieEntry> entries = getEntries(currentJobs);
        PieDataSet dataSet = new PieDataSet(entries, null);

        dataSet.setValueTextSize(8f);
        dataSet.setColors(getColors());

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        builder.setView(rootView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        JobStatisticsDialog.this.getDialog().cancel();
                    }
                });
        rootView.findViewById(R.id.job_stats_get_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String fromString = fromEditText.getText().toString();
                String toString = toEditText.getText().toString();
                Date fromDate = null;
                Date toDate = null;
                try {
                    fromDate = df.parse(fromString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    toDate = df.parse(toString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<Job> toDraw = getJobs(fromDate, toDate);
                ArrayList<PieEntry> newEntries = getEntries(toDraw);
                PieDataSet newDataSet = new PieDataSet(newEntries, null);
                newDataSet.setValueTextSize(8f);
                newDataSet.setColors(getColors());
                PieData newData = new PieData(newDataSet);
                pieChart.setData(newData);
                pieChart.invalidate();
            }
        });
        return builder.create();
    }

    private ArrayList<PieEntry> getEntries(ArrayList<Job> current) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i = 0 ; i < current.size() ; i ++){
            ArrayList<Task> tasks = currentJobsTasks.get(current.get(i));
            int currentTotalTime = 0;
            for(Task task: tasks){
                currentTotalTime += task.getTimeSpentInSeconds()/3600;
            }
            entries.add(new PieEntry(currentTotalTime, current.get(i).getTitle()));
        }
        return entries;
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

    private ArrayList<Job> getJobs(Date from, Date to){
        ArrayList<Job> jobs = new ArrayList<>();
        if(from == null && to == null) {
            return currentJobs;
        }
        else if(from == null){
            for(int i = 0 ; i < currentJobs.size() ; i ++){
                Job current = currentJobs.get(i);
                if(current.getDeadline().compareTo(to) == -1 || current.getDeadline().compareTo(to) == 0){
                    jobs.add(current);
                }
            }
        }
        else if(to == null){
            for(int i = 0 ; i < currentJobs.size() ; i ++){
                Job current = currentJobs.get(i);
                if(current.getDeadline().compareTo(from) == 1 || current.getDeadline().compareTo(from) == 0){
                    jobs.add(current);
                }
            }
        }
        else{
            for(int i = 0 ; i < currentJobs.size() ; i ++){
                Job current = currentJobs.get(i);
                if((current.getDeadline().compareTo(from) == 1 || current.getDeadline().compareTo(from) == 0) &&(current.getDeadline().compareTo(to) == -1 || current.getDeadline().compareTo(to) == 0)){
                    jobs.add(current);
                }
            }
        }
        return jobs;
    }
}
