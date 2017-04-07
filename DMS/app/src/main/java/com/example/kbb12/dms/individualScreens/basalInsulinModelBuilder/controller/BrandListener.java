package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 01/02/2017.
 */
public class BrandListener implements TextWatcher {

    private BasalInsulinReadWriteModel model;

    public BrandListener(BasalInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        model.setBasalBrandName(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
