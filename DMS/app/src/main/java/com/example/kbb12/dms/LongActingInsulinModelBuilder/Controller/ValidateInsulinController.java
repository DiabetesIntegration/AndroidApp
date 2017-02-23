package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.InsulinModelBuilderActivity;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.MainMenu.MainMenuActivity;
import com.example.kbb12.dms.Model.Insulin.DuplicateDoseException;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class ValidateInsulinController implements View.OnClickListener {

    Activity currentActivity;
    LongActingInsulinReadWriteModel model;

    public ValidateInsulinController(Activity currentActivity,LongActingInsulinReadWriteModel model){
        this.currentActivity=currentActivity;
        this.model=model;
    }
    @Override
    public void onClick(View v) {
        if(model.getLongActingBrandName()==null){
            model.setError("You need to enter the brand name of your long acting insulin before moving on");
            return;
        }
        List<LongActingInsulinEntry> doses=model.getTempDoses();
        for(LongActingInsulinEntry dose:doses){
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
