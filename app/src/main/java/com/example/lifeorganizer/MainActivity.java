package com.example.lifeorganizer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lifeorganizer.Adapters.ViewPagerAdapter;
import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.JobFragment;
import com.example.lifeorganizer.fragments.dummy.DummyContent;

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

    }


    @Override
    public void onBackPressed() {
        //FragmentHabit fragment = (FragmentHabit) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);

        FragmentHabit fragment = (FragmentHabit) viewPagerAdapter.habitFragment;
        /*switch (viewPagerAdapter.currentFragment){
            case TODO:
                Toast.makeText(getApplicationContext(),"TODO",Toast.LENGTH_SHORT).show();
                break;
            case Habit:
                Toast.makeText(getApplicationContext(),"Habit",Toast.LENGTH_SHORT).show();
                break;
        }*/
        if (fragment.getChildFragmentManager().getBackStackEntryCount() != 0 &&
               viewPagerAdapter.currentFragment == ViewPagerAdapter.FRAGMENT_TYPE.Habit) {
            fragment.getChildFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}