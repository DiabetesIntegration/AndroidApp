package com.example.kbb12.dms.takeInsulin.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadWriteModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class AmountTakenListener implements TextWatcher {

    private TakeInsulinReadWriteModel model;

    public AmountTakenListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        model.setAmountTaken(Double.parseDouble(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
