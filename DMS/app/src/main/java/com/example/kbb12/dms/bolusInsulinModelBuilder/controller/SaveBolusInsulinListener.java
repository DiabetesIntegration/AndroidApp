package com.example.kbb12.dms.bolusInsulinModelBuilder.controller;

import android.view.View;

import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 11/03/2017.
 */
public class SaveBolusInsulinListener implements View.OnClickListener {

    private BolusInsulinReadWriteModel model;

    public SaveBolusInsulinListener(BolusInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onClick(View v) {
        model.saveValues();
    }
}
