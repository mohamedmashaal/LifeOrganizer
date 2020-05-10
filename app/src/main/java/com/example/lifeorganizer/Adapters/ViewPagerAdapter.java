package com.example.lifeorganizer.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.BottomNavigationView;

import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentDiary;
import com.example.lifeorganizer.fragments.FragmentEvent;
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.FragmentTodo;
import com.example.lifeorganizer.fragments.JobFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    //private Fragment todoFragment, habitFragment;
    public Fragment todoFragment, habitFragment, eventFragment, jobFragment, diaryFragment;
    public enum  FRAGMENT_TYPE {Habit,TODO,EVENT ,Job,Diary};
    public FRAGMENT_TYPE currentFragment = FRAGMENT_TYPE.TODO;

    private BottomNavigationView navigationView;

    public ViewPagerAdapter(FragmentManager fm, BottomNavigationView navigationView) {
        super(fm);
        this.navigationView = navigationView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (todoFragment == null) {
                    todoFragment = new FragmentTodo();
                }
                return todoFragment;
            case 1:
                if (habitFragment == null) {
                    habitFragment = new FragmentHabit();
                }
                return habitFragment;
            case 2:
                if(jobFragment == null){
                    jobFragment = new JobFragment();
                }
                return jobFragment;
            case 3:
                if (eventFragment == null) {
                    eventFragment = new FragmentEvent();
                }
                return eventFragment;
            case 4:
                if (diaryFragment == null) {
                    diaryFragment = new FragmentDiary();
                }
                return diaryFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    private ViewPager.OnPageChangeListener listner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    navigationView.getMenu().findItem(R.id.navigation_todo).setChecked(true);
                    currentFragment = FRAGMENT_TYPE.TODO;
                    break;
                case 1:
                    navigationView.getMenu().findItem(R.id.navigation_habit).setChecked(true);
                    currentFragment = FRAGMENT_TYPE.Habit;
                    break;
                case 2:
                    navigationView.getMenu().findItem(R.id.navigation_job).setChecked(true);
                    currentFragment = FRAGMENT_TYPE.Job;
                    break;
                case 3:
                    navigationView.getMenu().findItem(R.id.navigation_event).setChecked(true);
                    currentFragment = FRAGMENT_TYPE.EVENT;
                    break;
                case 4:
                    navigationView.getMenu().findItem(R.id.navigation_diary).setChecked(true);
                    currentFragment = FRAGMENT_TYPE.Diary;
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public ViewPager.OnPageChangeListener getListner (){
        return listner;
    }

}
