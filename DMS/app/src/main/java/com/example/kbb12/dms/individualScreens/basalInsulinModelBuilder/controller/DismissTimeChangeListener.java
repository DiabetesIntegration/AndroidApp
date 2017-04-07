package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 26/03/2017.
 */

public class DismissTimeChangeListener implements DialogInterface.OnDismissListener {

    private BasalInsulinReadWriteModel model;

    public DismissTimeChangeListener(BasalInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        model.cancelSelection();
    }
}
