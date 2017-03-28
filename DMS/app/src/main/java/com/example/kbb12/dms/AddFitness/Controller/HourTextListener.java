package com.example.kbb12.dms.AddFitness.Controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.AddFitness.IAddFitness;

/**
 * Created by Garry on 26/03/2017.
 */

public class HourTextListener implements TextWatcher{

    IAddFitness model;

    public HourTextListener(IAddFitness model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
        try {
            model.setDurHours(Integer.parseInt(cs.toString()));
        } catch(NumberFormatException e){
            model.setDurHours(0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
