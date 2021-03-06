package com.example.lifeorganizer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lifeorganizer.Adapters.ViewPagerAdapter;
import com.example.lifeorganizer.fragments.FragmentDiary;
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.FragmentNoteView;
import com.example.lifeorganizer.notifications.TaskNotification;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_todo:
//                    loadFragment(new FragmentTodo(), false);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_habit:
//                    loadFragment(new FragmentHabit(), false);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_job:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_event:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_diary:
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), navigation);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), navigation);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerAdapter.getListner());

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        createNotification();
    }

    private void createNotification() {
        TaskNotification notification = TaskNotification.getInstance(this);
        notification.setTime(22, 50);
        //TODO get tasks number and make string
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Log.i("date", formatter.format(date));
//        notification.createNotification("Hi there", new Date());
    }


    @Override
    public void onBackPressed() {
        //FragmentHabit fragment = (FragmentHabit) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);
        //TODO handle navigation

        /*switch (viewPagerAdapter.currentFragment){
            case TODO:
                Toast.makeText(getApplicationContext(),"TODO",Toast.LENGTH_SHORT).show();
                break;
            case Habit:
                Toast.makeText(getApplicationContext(),"Habit",Toast.LENGTH_SHORT).show();
                break;
        }*/

        if(viewPagerAdapter.currentFragment == ViewPagerAdapter.FRAGMENT_TYPE.Habit) {
            FragmentHabit fragment = (FragmentHabit) viewPagerAdapter.habitFragment;
            if (fragment.getChildFragmentManager().getBackStackEntryCount() != 0) {
                fragment.getChildFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        } else if(viewPagerAdapter.currentFragment == ViewPagerAdapter.FRAGMENT_TYPE.Diary){
            FragmentDiary fragment = (FragmentDiary) viewPagerAdapter.diaryFragment;
            if (fragment.getChildFragmentManager().getBackStackEntryCount() != 0) {
                fragment.getChildFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }
}