package com.example.kbb12.dms.mainMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageButton takeInsulinButton =(ImageButton)findViewById(R.id.takeInsulinButton);
        takeInsulinButton.setOnClickListener(new TakeInsulinLauncher(this));
        ModelHolder.model.logModels();
        if(getIntent().getBooleanExtra("NotificationLaunch",false)){
            Intent nextIntent = new Intent(this, TakeInsulin.class);
            startActivity(nextIntent);
        }
    }
}
