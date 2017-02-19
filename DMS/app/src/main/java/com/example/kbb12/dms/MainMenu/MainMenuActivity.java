package com.example.kbb12.dms.MainMenu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.UserModel;
import com.example.kbb12.dms.NotificationCreator.NotificationCreator;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;

import java.util.Calendar;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        UserModel model=ModelHolder.model;
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        for(LongActingInsulinEntry dose:model.getDoses()){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, dose.getHour());
            calendar.set(Calendar.MINUTE, dose.getMinute());
            calendar.set(Calendar.SECOND, 0);
            Intent pendInt = new Intent();
            pendInt.setAction("com.DMS.longActingInsulinNotification");
            pendInt.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                    pendInt, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pi);
        }
    }
}
