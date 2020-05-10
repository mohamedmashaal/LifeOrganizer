package com.example.lifeorganizer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lifeorganizer.R;

public class AddNoteDialog extends DialogFragment {

    private IAddNoteDialog iDialog;
    private FragmentManager fm;


    public void createDialog(FragmentManager fm, IAddNoteDialog iDialog) {
        this.fm = fm;
        this.iDialog = iDialog;
    }

    public void showDialog() {
        this.show(fm, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_add_note, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Add Note", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AddNoteDialog.this.getDialog().cancel();
                    }
                });

        final AlertDialog dialog1 = builder.create();
        dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = (Button) dialog1.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
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
                            dialog1.dismiss();
                            iDialog.onPositiveClicked(noteTitle, noteBody);
                        }
                    }
                });
            }
        });
        return dialog1;
    }
}