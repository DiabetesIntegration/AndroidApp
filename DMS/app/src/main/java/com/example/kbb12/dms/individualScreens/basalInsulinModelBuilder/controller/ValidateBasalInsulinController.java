package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;
import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.BolusInsulinModelBuilder;
import com.example.kbb12.dms.database.basalInsulinModel.BasalInsulinEntry;
import com.example.kbb12.dms.database.basalInsulinModel.DuplicateDoseException;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class ValidateBasalInsulinController implements View.OnClickListener {

    Activity currentActivity;
    BasalInsulinReadWriteModel model;

    public ValidateBasalInsulinController(Activity currentActivity, BasalInsulinReadWriteModel model){
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
        if(doses.size()<1){
            model.setError("You must add at least one dosage for your basal insulin.");
            return;
        }
        for(BasalInsulinEntry dose:doses){
            if(dose.getDose()<=0){
                model.setError("You must enter a dosage greater than zero for each entry");
                return;
            }
        }
        try {
            model.saveDoses();
            currentActivity.startActivity(new Intent(currentActivity, BolusInsulinModelBuilder.class));
            currentActivity.finish();
        }catch (DuplicateDoseException e){
            model.setError("There can not be two doses taken at the same time.");
        }
    }
}
