package com.example.lifeorganizer.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.BottomNavigationView;

import com.example.lifeorganizer.R;
import com.example.lifeorganizer.fragments.FragmentHabit;
import com.example.lifeorganizer.fragments.FragmentTodo;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    //private Fragment todoFragment, habitFragment;
    public Fragment todoFragment, habitFragment, currentFragment;

    private BottomNavigationView navigationView;

    public ViewPagerAdapter(FragmentManager fm, BottomNavigationView navigationView) {
        super(fm);
        this.navigationView = navigationView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (todoFragment == null)
                    todoFragment = new FragmentTodo();
                    currentFragment = todoFragment;
                return todoFragment;
            case 1:
                if (habitFragment == null)
                    habitFragment = new FragmentHabit();
                    currentFragment = habitFragment;
                return habitFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
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
                    break;
                case 1:
                    navigationView.getMenu().findItem(R.id.navigation_habit).setChecked(true);
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
