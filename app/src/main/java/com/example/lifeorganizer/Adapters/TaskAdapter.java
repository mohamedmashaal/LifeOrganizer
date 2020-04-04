package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentTodo;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<FragmentTodo.task> {

    public TaskAdapter(Context context, ArrayList<FragmentTodo.task> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        FragmentTodo.task currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.task_name_text);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        miwokTextView.setText(currentWord.name);

        EditText timeField = listItemView.findViewById(R.id.task_time_spent_field);
        timeField.setText(currentWord.time);

        CheckBox checkBox = listItemView.findViewById(R.id.task_checkbox);
        checkBox.setChecked(currentWord.status);

        // Find the TextView in the list_item.xml layout with the ID default_text_view.

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.*/
        return listItemView;
    }
}
