package com.example.lifeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.MenuItem;

import com.example.lifeorganizer.Adapters.HabitsAdapter;
import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.fragments.FragmentTodo;
import com.example.lifeorganizer.fragments.FragmentHabit;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_todo:
                    loadFragment(new FragmentTodo(), false);
                    return true;
                case R.id.navigation_habit:
                    loadFragment(new FragmentHabit(), false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new FragmentTodo(), false);

//        recyclerView = findViewById(R.id.recyclerview_tasks);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        buttonAddTask = findViewById(R.id.floating_button_add);
//        buttonAddTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        showHabits();
    }

    private void showHabits(){
        HabitManager.getInstance(getApplicationContext()).getHabits(new AfterGetHabits() {
            @Override
            public void afterGetHabits(List<Habit> habits) {
                HabitsAdapter adapter = new HabitsAdapter(MainActivity.this, habits);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void loadFragment(Fragment fragment, boolean backStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (backStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
