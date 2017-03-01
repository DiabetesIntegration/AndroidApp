package com.example.kbb12.dms.MainMenu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.UserModel;
import com.example.kbb12.dms.NotificationCreator.NotificationCreator;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.TakeInsulin.TakeInsulin;

import java.util.Calendar;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageButton takeInsulinButton =(ImageButton)findViewById(R.id.takeInsulinButton);
        takeInsulinButton.setOnClickListener(new TakeInsulinLauncher(this));
        if(getIntent().getBooleanExtra("NotificationLaunch",false)){
            Intent nextIntent = new Intent(this, TakeInsulin.class);
            startActivity(nextIntent);
        }
    }
}
