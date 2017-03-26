package com.example.kbb12.dms.AddFitness.Controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.kbb12.dms.AddFitness.IAddFitness;

/**
 * Created by Garry on 26/03/2017.
 */

public class MinuteTextListener implements TextWatcher {

    IAddFitness model;

    public MinuteTextListener(IAddFitness model){
        this.model=model;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
        model.setDurMins(Integer.parseInt(cs.toString()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
