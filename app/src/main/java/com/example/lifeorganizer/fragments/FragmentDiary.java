package com.example.lifeorganizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.lifeorganizer.Adapters.DiaryListAdapter;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddNoteDialog;
import com.example.lifeorganizer.dialogs.IAddNoteDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentDiary extends Fragment {

    private FragmentActivity myContext;
    private ListView listview;
    private DiaryListAdapter mAdapter;
    // TODO  list of note instead
    List<DummyNote> notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((FloatingActionButton) view.findViewById(R.id.fb_add_note)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoteDialog mainDialog = new AddNoteDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddNoteDialog() {

                    @Override
                    public void onPositiveClicked(String title, String body) {
                        //TODO add the not in database

                        /*final Habit habit = new Habit(title, description, daysMask, hrsPerWeek, startDate);
                        HabitManager.getInstance(getActivity()).createHabit(habit, new AfterCreateHabit() {
                            @Override
                            public void afterCreateHabit() {
                                habitList.add(habit);
                                mAdapter.notifyDataSetChanged();
                            }
                        });*/

                        notes.add(new DummyNote(title,body,new Date()));
                        mAdapter.notifyDataSetChanged();

                    }
                });
                mainDialog.showDialog();
            }
        });
        //TODO load notes list from database look TODOFragment Example
        notes = new ArrayList<>();
        notes.add(new DummyNote("note1", "note1 details\nline 1",new Date()));
        notes.add(new DummyNote("note2", "note2 details\nline 23",new Date()));
        mAdapter = new DiaryListAdapter(getActivity(),notes,FragmentDiary.this);
        listview = view.findViewById(R.id.notesListView);
        listview.setItemsCanFocus(true);
        listview.setAdapter(mAdapter);


    }
    public class DummyNote{
        public DummyNote(String title,String body,Date date){
            this.title = title;
            this.body = body;
            this.date = date;
        }
        public String title;
        public String body;
        public Date date;
    }

}
