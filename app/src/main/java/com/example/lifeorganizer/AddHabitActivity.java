package com.example.lifeorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifeorganizer.Backend.AfterCreateHabit;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;

import java.util.Date;

public class AddHabitActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDesc);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHabit();
            }
        });
    }

    private void createHabit() {
        final String sTitle = editTextTitle.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("com.example.mytodo.Task Required");
            editTextTitle.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc Required");
            editTextDesc.requestFocus();
            return;
        }

        Habit habit = new Habit(sTitle, sDesc,0, 0, new Date());

        HabitManager.getInstance(getApplicationContext()).createHabit(habit, new AfterCreateHabit() {
            @Override
            public void afterCreateHabit() {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Habit Created", Toast.LENGTH_LONG).show();
            }
        });
    }
}
