package com.example.kbb12.dms.addFitness.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 28-Mar-17.
 */

public class DismissDateListener implements DialogInterface.OnDismissListener {

    private AddFitnessReadWriteModel model;

    public DismissDateListener(AddFitnessReadWriteModel model) {
        this.model = model;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        model.setActDateToChange(false);
    }
}