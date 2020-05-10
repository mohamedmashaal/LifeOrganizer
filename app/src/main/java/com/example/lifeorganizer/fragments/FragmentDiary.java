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
import android.widget.Button;
import android.widget.ListView;


import com.example.lifeorganizer.Adapters.DiaryListAdapter;
import com.example.lifeorganizer.Backend.AfterCreateDiaryNote;
import com.example.lifeorganizer.Backend.AfterGetDiaryNotes;
import com.example.lifeorganizer.Backend.DiaryManager;
import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddNoteDialog;
import com.example.lifeorganizer.dialogs.IAddNoteDialog;
import com.example.lifeorganizer.dialogs.ISelectDiaryDayDialog;
import com.example.lifeorganizer.dialogs.SelectDiaryDayDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentDiary extends Fragment {

    private FragmentActivity myContext;
    private ListView listview;
    private DiaryListAdapter mAdapter;
    // TODO  list of note instead
    List<DiaryNote> notes;

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
                        final DiaryNote note = new DiaryNote(title, body, new Date());
                        DiaryManager.getInstance(getActivity()).createDiaryNote(note, new AfterCreateDiaryNote() {
                            @Override
                            public void afterCreateDiaryNote(DiaryNote note1) {
                                notes.add(note1);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                mainDialog.showDialog();
            }
        });

        Button allBtn = view.findViewById(R.id.diary_all_btn);
        Button selectBtn = view.findViewById(R.id.diary_select_btn);

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllNotes();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDiaryDayDialog selectDialog = new SelectDiaryDayDialog();
                selectDialog.createDialog(getActivity().getSupportFragmentManager(), new ISelectDiaryDayDialog() {

                    @Override
                    public void onPositiveClicked(Date date) {
                        loadDayNotes(date);
                    }
                });
                selectDialog.showDialog();
            }
        });

        listview = view.findViewById(R.id.notesListView);
        loadAllNotes();
        /*DiaryManager diaryManager= DiaryManager.getInstance(getActivity());
        diaryManager.getDiaryNotes(new AfterGetDiaryNotes() {
            @Override
            public void afterGetDiaryNotes(List<DiaryNote> diaryNotes) {
                notes = diaryNotes;
                mAdapter = new DiaryListAdapter(getActivity(),notes,FragmentDiary.this);
                listview.setItemsCanFocus(true);
                listview.setAdapter(mAdapter);
            }
        });*/


    }
    private void loadAllNotes(){
        DiaryManager diaryManager= DiaryManager.getInstance(getActivity());
        diaryManager.getDiaryNotes(new AfterGetDiaryNotes() {
            @Override
            public void afterGetDiaryNotes(List<DiaryNote> diaryNotes) {
                notes = diaryNotes;
                mAdapter = new DiaryListAdapter(getActivity(),notes,FragmentDiary.this);
                listview.setItemsCanFocus(true);
                listview.setAdapter(mAdapter);
            }
        });
    }

    private void loadDayNotes(Date date){
        DiaryManager diaryManager= DiaryManager.getInstance(getActivity());
        diaryManager.getDiaryNotes(date,new AfterGetDiaryNotes() {
            @Override
            public void afterGetDiaryNotes(List<DiaryNote> diaryNotes) {
                notes = diaryNotes;
                mAdapter = new DiaryListAdapter(getActivity(),notes,FragmentDiary.this);
                listview.setItemsCanFocus(true);
                listview.setAdapter(mAdapter);
            }
        });
    }

}
