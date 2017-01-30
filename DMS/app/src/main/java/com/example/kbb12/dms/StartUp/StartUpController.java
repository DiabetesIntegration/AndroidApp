package com.example.kbb12.dms.StartUp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.InsulinModelBuilderActivity;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class StartUpController implements View.OnClickListener {

    private Activity currentActivity;

    public StartUpController(Activity currentActivity){
        this.currentActivity=currentActivity;
    }

    @Override
    public void onClick(View v) {
        currentActivity.startActivity(new Intent(currentActivity, InsulinModelBuilderActivity.class));
    }
}
