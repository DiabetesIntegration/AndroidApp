package com.example.kbb12.dms.basalInsulinModelBuilder.controller;

import android.view.View;

import com.example.kbb12.dms.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 23/02/2017.
 */
public class DeleteEntryListener implements View.OnClickListener {

    private BasalInsulinReadWriteModel model;
    private Integer entryNumber;

    public DeleteEntryListener(BasalInsulinReadWriteModel model,int entryNumber){
        this.model=model;
        this.entryNumber=entryNumber;
    }

    @Override
    public void onClick(View v) {
        model.setDoseToBeDeleted(entryNumber);
    }
}
