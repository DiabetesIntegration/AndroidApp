package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.content.DialogInterface;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 23/02/2017.
 */
public class CancelDeleteListener implements DialogInterface.OnClickListener {

    private BasalInsulinReadWriteModel model;

    public CancelDeleteListener(BasalInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        model.cancelDelete();
    }
}
