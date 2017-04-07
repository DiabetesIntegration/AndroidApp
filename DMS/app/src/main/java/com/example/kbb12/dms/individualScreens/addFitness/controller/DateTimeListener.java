package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.view.View;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 26/03/2017.
 */

public class DateTimeListener implements View.OnClickListener {

    AddFitnessReadWriteModel model;

    public DateTimeListener(AddFitnessReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onClick(View view) {
        model.setActDateToChange(true);
        //model.setActTimeToChange(true);
    }
}
