package com.example.kbb12.dms.addFitness.controller;

import android.view.View;

import com.example.kbb12.dms.addFitness.IAddFitness;

/**
 * Created by Garry on 26/03/2017.
 */

public class DateTimeListener implements View.OnClickListener {

    IAddFitness model;

    public DateTimeListener(IAddFitness model){
        this.model=model;
    }

    @Override
    public void onClick(View view) {
        model.setActDateToChange(true);
        //model.setActTimeToChange(true);
    }
}
