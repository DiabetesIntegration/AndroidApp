package com.example.kbb12.dms.startUp;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.kbb12.dms.bluetooth.BluetoothService;
import com.example.kbb12.dms.mainMenu.MainMenuActivity;
import com.example.kbb12.dms.model.UserModel;
import com.example.kbb12.dms.R;

/*
This will be the activity that creates the user model at the start and passes it on.
 */
public class MainActivity extends AppCompatActivity {

    private UserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Creates user model.
        ModelHolder.model = new UserModel(this,getSharedPreferences("fitnessprefs", MODE_PRIVATE));
        model = ModelHolder.model;
        if(model.getDoses().size()>0){
            Intent nextIntent = new Intent(this, MainMenuActivity.class);
            if(getIntent().getBooleanExtra("NotificationLaunch",false)){
                nextIntent.putExtra("NotificationLaunch",true);
            }
            startActivity(nextIntent);
            finish();
        }
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new StartUpController(this));

    }

}
