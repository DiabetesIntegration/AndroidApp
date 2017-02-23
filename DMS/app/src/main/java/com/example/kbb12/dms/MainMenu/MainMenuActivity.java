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
        model.getError();
    }
}
