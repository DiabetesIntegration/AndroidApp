package com.example.kbb12.dms.basalInsulinModelBuilder.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;
import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;
import com.example.kbb12.dms.mainMenu.MainMenuActivity;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class ValidateInsulinController implements View.OnClickListener {

    Activity currentActivity;
    BasalInsulinReadWriteModel model;

    public ValidateInsulinController(Activity currentActivity,BasalInsulinReadWriteModel model){
        this.currentActivity=currentActivity;
        this.model=model;
    }
    @Override
    public void onClick(View v) {
        if(model.getBasalBrandName()==null){
            model.setError("You need to enter the brand name of your basal insulin before moving on");
            return;
        }
        List<BasalInsulinEntry> doses=model.getTempDoses();
        for(BasalInsulinEntry dose:doses){
            if(dose.getDose()<=0){
                model.setError("You must enter a dosage greater than zero for each entry");
                return;
            }
        }
        try {
            model.saveDoses();
            Intent setUpAlerts = new Intent("com.DMS.timedAlertCreator");
            currentActivity.sendBroadcast(setUpAlerts);
            currentActivity.startActivity(new Intent(currentActivity, MainMenuActivity.class));
            currentActivity.finish();
        }catch (DuplicateDoseException e){
            model.setError("There can not be two doses taken at the same time.");
        }
    }
}
