package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 26/03/2017.
 */

public class MinuteTextListener implements TextWatcher {

    AddFitnessReadWriteModel model;

    public MinuteTextListener(AddFitnessReadWriteModel model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
        try {
            model.setDurMins(Integer.parseInt(cs.toString()));
        } catch(NumberFormatException e){
            model.setDurMins(0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
