package com.example.kbb12.dms.AddFitness.Controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.AddFitness.IAddFitness;

/**
 * Created by Garry on 28-Mar-17.
 */

public class DismissDateListener implements DialogInterface.OnDismissListener {

    private IAddFitness model;

    public DismissDateListener(IAddFitness model) {
        this.model = model;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        model.setActDateToChange(false);
    }
}