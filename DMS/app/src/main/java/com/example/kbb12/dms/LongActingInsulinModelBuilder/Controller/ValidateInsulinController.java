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
        List<LongActingInsulinEntry> doses=model.getInsulinEntries();
        String invalidEntries="";
        if(doses.size()<1){
            model.setError("You must enter at least one type of insulin which you use");
            return;
        }
        for(int i=0;i<doses.size();i++){
            if(doses.get(i).getBrandName()==null){
                invalidEntries=invalidEntries+i+",";
            }
        }
        if(invalidEntries.equals("")){
            //Launch next Activity
            return;
        }
        invalidEntries=invalidEntries.substring(0,invalidEntries.length()-1);
        model.setError("The following entries have invalid names: "+invalidEntries);
        return;
    }
}
