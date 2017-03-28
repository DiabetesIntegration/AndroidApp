package com.example.kbb12.dms.bolusInsulinModelBuilder.controller;

import android.widget.CompoundButton;

import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class KnowsISFListener implements CompoundButton.OnCheckedChangeListener {
    private BolusInsulinReadWriteModel model;

    public KnowsISFListener(BolusInsulinReadWriteModel model){
        this.model=model;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        model.setKnowsISF(isChecked);
    }
}
