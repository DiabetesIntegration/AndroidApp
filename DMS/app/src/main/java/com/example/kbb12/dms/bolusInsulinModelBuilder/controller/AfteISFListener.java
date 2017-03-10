package com.example.kbb12.dms.bolusInsulinModelBuilder.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class AfteISFListener implements TextWatcher {

    private BolusInsulinReadWriteModel model;

    public AfteISFListener(BolusInsulinReadWriteModel model){
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
        model.setAfteISF(Double.parseDouble(s.toString()));
    }
}
