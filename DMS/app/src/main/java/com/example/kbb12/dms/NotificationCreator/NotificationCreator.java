package com.example.kbb12.dms.notificationCreator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.MainActivity;

/**
 * Created by kbb12 on 19/02/2017.
 */
public class NotificationCreator extends BroadcastReceiver {

    public NotificationCreator(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.take_insulin_icon)
                        .setContentTitle("It is time to take insulin")
                        .setContentText("DMS");
        int mNotificationId = 001;
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("NotificationLaunch",true);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(resultPendingIntent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        //Sends the broadcast message to make sure the next notification is set
        Intent setUpAlerts = new Intent("com.DMS.timedAlertCreator");
        context.sendBroadcast(setUpAlerts);
    }
}
