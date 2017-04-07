package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class LunInsulinListener implements TextWatcher {

    private BolusInsulinReadWriteModel model;

    public LunInsulinListener(BolusInsulinReadWriteModel model){
        this.model=model;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length()>0) {
            model.setLunInsulin(Double.parseDouble(s.toString()));
        }else{
            model.setLunInsulin(null);
        }
    }
}
