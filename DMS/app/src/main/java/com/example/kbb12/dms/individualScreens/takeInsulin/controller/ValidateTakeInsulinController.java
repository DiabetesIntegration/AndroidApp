package com.example.kbb12.dms.individualScreens.takeInsulin.controller;

import android.app.Activity;
import android.view.View;

import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;
import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadWriteModel;

/**
 * Created by kbb12 on 01/03/2017.
 */
public class ValidateTakeInsulinController implements View.OnClickListener {

    private TakeInsulinReadWriteModel model;
    private Activity current;

    public ValidateTakeInsulinController(TakeInsulinReadWriteModel model,Activity current){
        this.model=model;
        this.current=current;
    }

    @Override
    public void onClick(View v) {
        if(model.getAmountTaken().equals(0.0)){
            model.setError("You can not add an insulin amount of 0");
            return;
        }
        if(model.getTypeTaken().equals(TakeInsulinReadModel.InsulinType.NOT_SET)){
            model.setError("You must specify what type of insulin you are taking");
            return;
        }
        model.takeInsulin();
        current.finish();
    }
}
