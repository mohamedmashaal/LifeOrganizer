package com.example.lifeorganizer.dialogs;

import com.example.lifeorganizer.Data.Task;

import java.util.ArrayList;
import java.util.Date;

public interface IAddJobDialog {

    void onPositiveClicked(String title, String description, Date deadline, ArrayList<Task> tasks);

}
