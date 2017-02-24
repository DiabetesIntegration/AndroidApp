package com.example.kbb12.dms.NotificationCreator;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.Insulin.ILongActingInsulinDatabase;
import com.example.kbb12.dms.Model.Insulin.LongActingInsulinDatabase;
import com.example.kbb12.dms.Model.UserModel;
import com.example.kbb12.dms.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 19/02/2017.
 */
public class TimedAlertCreator extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.take_insulin_button)
                        .setContentTitle("Setting up alerts")
                        .setContentText("DMS");
        int mNotificationId = 002;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        ILongActingInsulinDatabase database = new LongActingInsulinDatabase(context, UserModel.versionNumber);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        List<LongActingInsulinEntry> entries = database.getEntries();
        Calendar currentTime = Calendar.getInstance();
        Calendar nextTime =null;
        Calendar entryTime;
        Calendar minEntryTime=Calendar.getInstance();
        minEntryTime.set(Calendar.HOUR_OF_DAY, entries.get(0).getHour());
        minEntryTime.set(Calendar.MINUTE, entries.get(0).getMinute());
        minEntryTime.set(Calendar.SECOND, 0);
        for(int i=0;i<entries.size();i++) {
            LongActingInsulinEntry entry=entries.get(i);
            entryTime = Calendar.getInstance();
            entryTime.set(Calendar.HOUR_OF_DAY, entry.getHour());
            entryTime.set(Calendar.MINUTE, entry.getMinute());
            entryTime.set(Calendar.SECOND, 0);
            if(entryTime.getTimeInMillis()>currentTime.getTimeInMillis()){
                if(null==nextTime||nextTime.getTimeInMillis()>entryTime.getTimeInMillis()){
                    nextTime=entryTime;
                }
            }
            if(entryTime.getTimeInMillis()<minEntryTime.getTimeInMillis()){
                minEntryTime=entryTime;
            }
        }
        if(null==nextTime){
            //Next notification is tomorrow
            nextTime=minEntryTime;
            nextTime.add(Calendar.DATE,1);
        }
        Intent pendInt = new Intent();
        pendInt.setAction("com.DMS.longActingInsulinNotification");
        pendInt.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0,
                pendInt, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, nextTime.getTimeInMillis(), pi);
    }
}
