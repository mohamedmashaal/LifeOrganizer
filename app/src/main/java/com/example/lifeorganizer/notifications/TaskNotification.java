package com.example.lifeorganizer.notifications;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskNotification {

    private static TaskNotification taskNotification;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    final Calendar myCalendar = Calendar.getInstance();
    private Activity activity;
    private Date date;
    private Notification notification;

    public static TaskNotification getInstance(Activity activity){
        if (taskNotification == null)
            taskNotification = new TaskNotification(activity);

        return taskNotification;
    }

    public static TaskNotification getInstance(){
        return taskNotification;
    }

    public void setTime (int hour, int minutes){
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        myCalendar.set(year, month, day, hour, minutes, 0);

        this.date = myCalendar.getTime();
        createNotification(this.date);
    }

    private TaskNotification(Activity activity) {
        this.activity = activity;

        setTime(18, 0);
    }


    public void createNotification(Date date) {
        Date now = new Date();
        if (date.getTime() < now.getTime())
            return;

        scheduleNotification(this.date.getTime());
    }

    private void scheduleNotification(long delay) {
        Intent notificationIntent = new Intent(activity, MyNotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
    }

    private Notification getNotification(String content) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, default_notification_channel_id);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_add);
        builder.setSound(alarmSound);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

    public void showNotification(final Context context, final Intent intent) {
        Log.i("myDate", "there");
        TaskManager.getInstance(activity).getTasks(this.date, new AfterGetTasks() {
            @Override
            public void afterGetTasks(List<Task> tasks) {
                int i = 0;
                for (Task task : tasks)
                    if (!task.isFinished()) i++;

                if (i > 0) {
                    String content = "Still have " + String.valueOf(i) + " unfinished tasks today";

                    notification = getNotification(content);
                    show(context, intent);
                } else {
                    notification = null;
                }
            }
        });
    }

    private void show(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);
    }

}
