package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 28-Mar-17.
 */

public class DismissTimeListener implements DialogInterface.OnDismissListener {

    private AddFitnessReadWriteModel model;

    public DismissTimeListener(AddFitnessReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        model.setActTimeToChange(false);
    }
}
