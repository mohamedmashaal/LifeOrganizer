package com.example.lifeorganizer.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;

import java.util.Date;
import java.util.List;

import static com.example.lifeorganizer.notifications.TaskNotification.NOTIFICATION_CHANNEL_ID;

public class MyNotificationPublisher extends BroadcastReceiver {
    private final static String default_notification_channel_id = "default";
    public static String NOTIFICATION_ID = "notification-id";

    public void onReceive(final Context context, final Intent intent) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification[] notification = {null};

        TaskManager.getInstance(context).getTasks(new Date(), new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                int i = 0;
                for (Task task : tasks)
                    if (!task.isFinished()) i++;

                if (i > 0) {
                    String content = "Still have " + String.valueOf(i) + " unfinished tasks today";
                    notification[0] = getNotification(context, content);
                } else {
                    String content = "You have no tasks for today";
                    notification[0] = getNotification(context, content);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    assert notificationManager != null;
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                int id = intent.getIntExtra(NOTIFICATION_ID, 0);
                assert notificationManager != null;
                notificationManager.notify(id, notification[0]);
            }
        });
    }

    private Notification getNotification(Context context, String content) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id);
        builder.setContentTitle("Tasks");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_add);
        builder.setSound(alarmSound);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

}