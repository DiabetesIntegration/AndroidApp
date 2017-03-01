package com.example.kbb12.dms.TakeInsulin.Controller;

import android.view.View;

import com.example.kbb12.dms.TakeInsulin.Model.TakeInsulinReadWriteModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class ChangeTimeTakenListener implements View.OnClickListener {

    TakeInsulinReadWriteModel model;

    public ChangeTimeTakenListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onClick(View v) {
        model.setDateToChange();
    }
}
