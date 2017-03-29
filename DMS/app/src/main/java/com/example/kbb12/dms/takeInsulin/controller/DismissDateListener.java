package com.example.kbb12.dms.takeInsulin.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadWriteModel;


/**
 * Created by kbb12 on 28/03/2017.
 */

public class DismissDateListener implements DialogInterface.OnDismissListener {

    private TakeInsulinReadWriteModel model;

    public DismissDateListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        model.setDateToChange(false);
    }
}
