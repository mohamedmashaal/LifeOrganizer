package com.example.lifeorganizer.Adapters;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;


import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> words) {
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
        final Task task = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView nameText = (TextView) listItemView.findViewById(R.id.task_name_text);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        //nameText.setText(currentWord.name);

        final EditText timeField = listItemView.findViewById(R.id.task_time_spent_field);
        //timeField.setText(currentWord.time+"");
        //timeField.requestFocus();

        CheckBox checkBox = listItemView.findViewById(R.id.task_checkbox);
        //checkBox.setChecked(currentWord.status);

/*        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentWord.status = isChecked;
                Toast.makeText(getContext(), currentWord.name+ "," + currentWord.time, Toast.LENGTH_SHORT).show();
                if(!isChecked) {
                    timeField.setText("0");
                    currentWord.time = 0;
                    timeField.setInputType(InputType.TYPE_NULL);
                } else {
                    timeField.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
*/
        // Find the TextView in the list_item.xml layout with the ID default_text_view.

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.*/
        return listItemView;
    }
}
