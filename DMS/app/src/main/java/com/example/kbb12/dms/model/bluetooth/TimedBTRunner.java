package com.example.kbb12.dms.model.bluetooth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by lidda on 27/03/2017.
 */

public class TimedBTRunner extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent pendInt = new Intent(context, BluetoothService.class);
        PendingIntent pi = PendingIntent.getService(context, 5, pendInt, 0);
//ToDo: Uncomment for later
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                10*60*1000,
                pi);
    }
}
