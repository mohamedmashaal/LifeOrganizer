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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Adapters.TaskAdapter;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.IAddTaskDialog;
import com.example.lifeorganizer.dialogs.AddTaskDialog;

import java.util.ArrayList;

public class FragmentTodo extends Fragment {

    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);


        /*// Create a list of words
        final ArrayList<task> tasks = new ArrayList<task>();
        for (int i = 0; i < 20; i++) {
            tasks.add(new task());
        }

        TaskAdapter adapter = new TaskAdapter(getActivity(), tasks);

        ListView listView = (ListView) getView().findViewById(R.id.task_list_view);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //view task information
                //Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();

            }
        });*/
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //final TextView textView = (TextView) view.findViewById(R.id.testDialog);

        ((FloatingActionButton) view.findViewById(R.id.fb_add_task)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog mainDialog = new AddTaskDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddTaskDialog() {
                    @Override
                    public void onPositiveClicked(String title, String description, String date) {

                        //textView.setText(date);

                        //TODO add new task here to db and view if success

                    }
                });
                mainDialog.showDialog();
            }
        });

        // Create a list of words
        final ArrayList<Task> tasks = new ArrayList<Task>();
       // for (int i = 0; i < 20; i++) {
//            tasks.add(new task());
//            tasks.get(i).time = i;
        //}

        TaskAdapter adapter = new TaskAdapter(getActivity(), tasks);

        ListView listView = (ListView) getView().findViewById(R.id.task_list_view);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //view task information
                //Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
