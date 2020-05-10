package com.example.lifeorganizer.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import static com.example.lifeorganizer.notifications.TaskNotification.NOTIFICATION_CHANNEL_ID;

public class MyNotificationPublisher extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Log.i("myDate", "here");
        TaskNotification.getInstance().showNotification(context, intent);

    }
}