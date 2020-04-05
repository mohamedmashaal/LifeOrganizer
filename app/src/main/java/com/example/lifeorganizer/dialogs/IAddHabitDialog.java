package com.example.lifeorganizer.dialogs;

import java.util.Date;

public interface IAddHabitDialog {

    void onPositiveClicked (String title, String description, String daysMask, int hrsPerWeek, Date startDate);

}
