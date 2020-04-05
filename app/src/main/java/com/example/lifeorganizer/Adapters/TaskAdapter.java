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
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_item, parent, false);
        }


        final Task task = getItem(position);
        final TextView nameText = (TextView) listItemView.findViewById(R.id.task_name_text);
        nameText.setText(task.getTitle());

        final EditText timeField = listItemView.findViewById(R.id.task_time_spent_field);
        timeField.setText(task.getTimeSpentInSeconds()/60+"");
        if(task.isFinished()){
            timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            timeField.setInputType(InputType.TYPE_NULL);
        }
        //TODO put change listener
        //timeField.requestFocus();

        final CheckBox checkBox = listItemView.findViewById(R.id.task_checkbox);
        checkBox.setChecked(task.isFinished());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO warning the user from uncheck
                task.setFinished(isChecked);

                //Toast.makeText(getContext(), currentWord.name+ "," + currentWord.time, Toast.LENGTH_SHORT).show();
                //TODO did set reflect in database?
                if(isChecked) {
                   timeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                } else {
                   timeField.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        // Find the TextView in the list_item.xml layout with the ID default_text_view.

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.*/
        return listItemView;
    }
}
