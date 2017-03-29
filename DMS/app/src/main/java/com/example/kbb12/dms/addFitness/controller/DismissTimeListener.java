package com.example.kbb12.dms.addFitness.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.addFitness.IAddFitness;

/**
 * Created by Garry on 28-Mar-17.
 */

public class DismissTimeListener implements DialogInterface.OnDismissListener {

    private IAddFitness model;

    public DismissTimeListener(IAddFitness model){
        this.model=model;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        model.setActTimeToChange(false);
    }
}
