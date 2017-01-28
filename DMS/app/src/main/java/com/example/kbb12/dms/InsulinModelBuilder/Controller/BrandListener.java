package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;

/**
 * Created by kbb12 on 28/01/2017.
 */
public class BrandListener implements TextWatcher {

    private int entryNumber;
    private InsulinReadWriteModel model;

    public BrandListener(int entryNumber,InsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        model.setBrand(entryNumber,s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
