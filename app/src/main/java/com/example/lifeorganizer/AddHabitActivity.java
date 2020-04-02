package com.example.lifeorganizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lifeorganizer.Backend.HabitManager;

public class AddHabitActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        editTextTask = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDesc);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHabit();
            }
        });
    }

    private void createHabit() {
        final String sTitle = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();

        if (sTitle.isEmpty()) {
            editTextTask.setError("com.example.mytodo.Task Required");
            editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc Required");
            editTextDesc.requestFocus();
            return;
        }

        HabitManager.getInstance(getApplicationContext()).c
    }
}
