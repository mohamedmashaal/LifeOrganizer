package com.example.lifeorganizer.dialogs;

import com.example.lifeorganizer.fragments.TaskHolder;

import java.util.ArrayList;
import java.util.Date;

public interface IEditJobDialog {

    void onPositiveClicked(String title, String description, Date deadline, ArrayList<TaskHolder> tasks);

}
