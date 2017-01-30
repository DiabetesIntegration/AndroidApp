package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;

/**
 * Created by kbb12 on 28/01/2017.
 */
public class BrandListener implements TextWatcher {

    private LongActingInsulinReadWriteModel model;

    public BrandListener(LongActingInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        model.setBrand(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
