package com.example.kbb12.dms.basalInsulinModelBuilder.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 01/02/2017.
 */
public class DoseListener implements TextWatcher {

    private int entryNumber;
    private BasalInsulinReadWriteModel model;

    public DoseListener(int entryNumber,BasalInsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try{
            model.setDose(Double.parseDouble(s.toString()),entryNumber);
        }catch (NumberFormatException e){
            //Could potentially alert the user here but that gets annoying so
            //instead just don't pass invalid data to model and alert user
            //when they try to move to the next screen.
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
