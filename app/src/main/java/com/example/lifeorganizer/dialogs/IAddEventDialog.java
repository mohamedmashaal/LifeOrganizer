package com.example.lifeorganizer.dialogs;

import java.util.Date;

public interface IAddEventDialog {

    void onPositiveClicked (String title, String description, int startHours, int startMins, int duration , Date startDate);

}
