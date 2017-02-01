package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.view.View;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class ValidateInsulinController implements View.OnClickListener {

    LongActingInsulinReadWriteModel model;

    public ValidateInsulinController(LongActingInsulinReadWriteModel model){
        this.model=model;
    }
    @Override
    public void onClick(View v) {
        if(model.getLongActingBrandName()==null){
            model.setError("You need to enter the brand name of your long acting insulin before moving on");
            return;
        }
        List<LongActingInsulinEntry> doses=model.getDoses();
        for(LongActingInsulinEntry dose:doses){
            if(dose.getDose()<=0){
                model.setError("You must enter a dosage greater than zero for each entry");
                return;
            }
        }
        //Launch Next Activity
    }
}
