package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;

/**
 * Created by kbb12 on 01/02/2017.
 */
public class DoseListener implements TextWatcher {

    private int entryNumber;
    private LongActingInsulinReadWriteModel model;

    public DoseListener(int entryNumber,LongActingInsulinReadWriteModel model){
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
            model.setError("I'm afraid the dose you tried to enter is not a number.");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
