package com.example.lifeorganizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class Helper {
    private static final Helper ourInstance = new Helper();

    public static Helper getInstance() {
        return ourInstance;
    }

    private Helper() {
    }

    public void loadFragment(Fragment fragment, FragmentManager fragmentManager, boolean backStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (backStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
