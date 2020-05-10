package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentDiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNoteDialog extends DialogFragment {

    private IEditNoteDialog iDialog;
    private FragmentManager fm;
    private DiaryNote note;

    public void createDialog(FragmentManager fm, IEditNoteDialog iDialog, DiaryNote note){
        this.fm = fm;
        this.iDialog = iDialog;
        this.note = note;
    }

    public void showDialog(){
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_add_note, null);
        TextView header = rootView.findViewById(R.id.add_note_header);
        header.setText("Edit Note");
        EditText titleText = rootView.findViewById(R.id.add_note_title);
        titleText.setText(note.getTitle());

        EditText bodyText = rootView.findViewById(R.id.add_note_body);
        bodyText.setText(note.getBody());


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Edit Note",null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditNoteDialog.this.getDialog().cancel();
                    }
                });
        final AlertDialog d = builder.create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = d.getButton(Dialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteTitle = ((EditText) rootView.findViewById(R.id.add_note_title)).getText().toString();
                        String noteBody = ((EditText) rootView.findViewById(R.id.add_note_body)).getText().toString();

                        if (noteBody.length() == 0) {
                            //Log.d("Empty Body", "\n======================\nI am here\n====================\n");
                            EditText bodyFiled = rootView.findViewById(R.id.add_note_body);
                            bodyFiled.setError("enter content of note");
                        } else {
                            if(noteTitle.length() == 0){
                                if(noteBody.length() <= 10){
                                    noteTitle = noteBody;
                                }else {
                                    noteTitle = noteBody.substring(0,10);
                                }
                            }
                            note.setTitle(noteTitle);
                            note.setBody(noteBody);
                            note.setCreatedAtDate(new Date());
                            d.dismiss();
                            iDialog.onPositiveClicked(note);
                        }
                    }
                });
            }
        });
        return d;
    }


}
