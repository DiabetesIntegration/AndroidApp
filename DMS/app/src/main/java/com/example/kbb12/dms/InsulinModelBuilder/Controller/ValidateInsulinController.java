package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.view.View;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class ValidateInsulinController implements View.OnClickListener {

    InsulinReadWriteModel model;

    public ValidateInsulinController(InsulinReadWriteModel model){
        this.model=model;
    }
    @Override
    public void onClick(View v) {
        //if (model.isInsulinValid()){
            //launch next activity
        //}
        //Need to get error up somehow
    }
}
