package com.example.lifeorganizer.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifeorganizer.Adapters.DiaryListAdapter;
import com.example.lifeorganizer.Adapters.HabitListAdapter;
import com.example.lifeorganizer.Backend.AfterEditHabit;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.EditHabitDialog;
import com.example.lifeorganizer.dialogs.EditNoteDialog;
import com.example.lifeorganizer.dialogs.IEditHabitDialog;
import com.example.lifeorganizer.dialogs.IEditNoteDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentNoteView extends Fragment {
    EditText titleField , bodyFiled;
    Button editBtn;
    private static DiaryListAdapter diaryListAdapter;
    private static FragmentDiary.DummyNote note;
    public static FragmentNoteView newInstance(FragmentDiary.DummyNote note, DiaryListAdapter diaryListAdapter) {
        FragmentNoteView.note = note;
        FragmentNoteView.diaryListAdapter = diaryListAdapter;
        FragmentNoteView fragment = new FragmentNoteView();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_note_view, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main);
        titleField = view.findViewById(R.id.note_title_filed);
        bodyFiled = view.findViewById(R.id.note_body_filed);
        editBtn = view.findViewById(R.id.note_edit_btn);

        titleField.setText(note.title);
        bodyFiled.setText(note.body);

        setEditListener();
    }

    private void setEditListener(){
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNoteDialog editDialog = new EditNoteDialog();
                editDialog.createDialog((getActivity()).getSupportFragmentManager(), new IEditNoteDialog() {
                    @Override
                    public void onPositiveClicked(FragmentDiary.DummyNote note) {
                        //TODO udate note in the database
                        /*TaskManager taskManager = TaskManager.getInstance(context);
                        taskManager.editTask(task, new AfterEditTask() {
                            @Override
                            public void afterEditTask() {
                            }
                        });*/
                        titleField.setText(note.title);
                        bodyFiled.setText(note.body);
                        diaryListAdapter.notifyDataSetChanged();
                    }
                }, note);
                editDialog.showDialog();
            }
        });
    }

}





