package com.example.kbb12.dms.notificationCreator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.model.database.DatabaseBuilder;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 19/02/2017.
 */
public class TimedAlertCreator extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseBuilder db = new DatabaseBuilder(context);
        IBasalInsulinModel database = db.getBasalInsulinModel();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        List<BasalInsulinEntry> entries = database.getEntries(true);
        Calendar currentTime = Calendar.getInstance();
        Calendar nextTime =null;
        Calendar entryTime;
        Calendar minEntryTime=Calendar.getInstance();
        minEntryTime.set(Calendar.HOUR_OF_DAY, entries.get(0).getHour());
        minEntryTime.set(Calendar.MINUTE, entries.get(0).getMinute());
        minEntryTime.set(Calendar.SECOND, 0);
        for(int i=0;i<entries.size();i++) {
            BasalInsulinEntry entry=entries.get(i);
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
        pendInt.setAction("com.DMS.basalInsulinNotification");
        pendInt.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0,
                pendInt, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, nextTime.getTimeInMillis(), pi);
    }
}
