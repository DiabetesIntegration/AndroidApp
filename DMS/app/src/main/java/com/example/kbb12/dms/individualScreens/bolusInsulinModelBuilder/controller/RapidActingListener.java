package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller;

import android.widget.CompoundButton;

import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class RapidActingListener implements CompoundButton.OnCheckedChangeListener {

    private BolusInsulinReadWriteModel model;

    public RapidActingListener(BolusInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        model.setRapidActing(isChecked);
    }
}
