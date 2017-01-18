package com.example.kbb12.dms.StartUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.Template.TemplateActivity;

/*
This will be the activity that creates the user model at the start and passes it on.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Creates user model.
        ModelHolder.model = new UserModel("60");
        Intent templateIntent = new Intent(this,TemplateActivity.class);
        //Passes the model to the intent.
        //templateIntent.putExtra("UserModel", newModel);
        //Launches the next activity.
        startActivity(templateIntent);
    }
}
