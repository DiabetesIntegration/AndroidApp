package com.example.kbb12.dms.model.improvementEngine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by kbb12 on 28/03/2017.
 */

public class ImprovementSetUp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent pendInt = new Intent();
        pendInt.setAction("com.DMS.basalImprovement");
        pendInt.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0,
                pendInt, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                24*60*60*1000,pi);
        Intent updateNow = new Intent("com.DMS.basalImprovement");
        context.sendBroadcast(updateNow);
    }
}
