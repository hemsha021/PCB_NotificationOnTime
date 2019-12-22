package com.example.notificationontime;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Channel for Simple Notification

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel simpleChannel = new NotificationChannel("SIMPLE",getString(R.string.simple_channel_name), NotificationManager.IMPORTANCE_HIGH);

            simpleChannel.setDescription(getString(R.string.simple_channel_description));

            NotificationManager simpleNotificationManager = getSystemService(NotificationManager.class);
            simpleNotificationManager.createNotificationChannel(simpleChannel);

        }

        //scheduleNotification(getNotification("5 second delay"));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification(getNotification("5 second delay"));

            }
        });
    }

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationReciever.class);
        notificationIntent.putExtra(NotificationReciever.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE,34);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis()+3000, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder mBuillder = new NotificationCompat.Builder(MainActivity.this,"SIMPLE")
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Activity Notification")
                .setContentText("This ia an Activity Notification with HIGH Priority and Channel ID equals to ACTIVITY  with lots and lots of text like in mail or messaging app")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This ia an Activity Notification with HIGH Priority and Channel ID equals to ACTIVITY with lots and lots of text like in mail or messaging app"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        return mBuillder.build();
    }
}
