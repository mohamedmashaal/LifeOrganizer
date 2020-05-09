package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lifeorganizer.Backend.AfterDeleteDiaryNote;
import com.example.lifeorganizer.Backend.AfterEditDiaryNote;
import com.example.lifeorganizer.Backend.DiaryManager;
import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.EditNoteDialog;
import com.example.lifeorganizer.dialogs.IEditNoteDialog;
import com.example.lifeorganizer.fragments.FragmentDiary;
import com.example.lifeorganizer.fragments.FragmentHabitView;
import com.example.lifeorganizer.fragments.FragmentNoteView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryListAdapter extends ArrayAdapter<DiaryNote> {
    List<DiaryNote> notes;
    DiaryListAdapter diaryAdapter;
    Context context;
    FragmentDiary diaryFragment;
    LayoutInflater mInflater;

    final String [] MONTHS = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep", "Oct","Nov","Dec"};

    public DiaryListAdapter(Context context, List<DiaryNote> notes, FragmentDiary fragment) {
        super(context, 0, notes);
        this.notes = notes;
        diaryAdapter = this;
        this.context = context;
        this.diaryFragment = fragment;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //listItemView = mInflater.inflate(R.layout.task_item, parent,false);
            listItemView = mInflater.inflate(R.layout.note_item, null);
        }
        DiaryNote note = getItem(position);
        Button deleteBtn = (Button) listItemView.findViewById(R.id.note_delete_btn);
        TextView titleView = listItemView.findViewById(R.id.note_title_text);
        TextView dateView = listItemView.findViewById(R.id.note_date_text);


        titleView.setText(note.getTitle());
        dateView.setText(getDateText(note.getCreatedAtDate()));

        setNameTextListener(titleView,note);



        setDeleteListener(deleteBtn,note);

        return listItemView;
    }

    private String getDateText(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String d = calendar.get(Calendar.DAY_OF_MONTH)+" ";
        d += MONTHS[calendar.get(Calendar.MONTH)] + " ";
        d += calendar.get(Calendar.YEAR);
        return  d;
    }


    private void setNameTextListener(final TextView titleView, final DiaryNote note){
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = diaryFragment.getChildFragmentManager().beginTransaction();
/*
                transaction.replace(R.id.habit_view_fragment_container,
                        FragmentHabitView.newInstance(habit));
*/
                transaction.add(R.id.note_view_fragment_container,
                        FragmentNoteView.newInstance(note,DiaryListAdapter.this));

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void setDeleteListener(final Button deleteBtn, final DiaryNote note){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Are you sure you want to delete this the note?")
                        .setTitle("Delete Note");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DiaryManager diaryManager = DiaryManager.getInstance(diaryFragment.getActivity());
                        diaryManager.deleteDiaryNote(note, new AfterDeleteDiaryNote() {
                            @Override
                            public void afterDeleteDiaryNote() {
                                notes.remove(note);
                                diaryAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}

