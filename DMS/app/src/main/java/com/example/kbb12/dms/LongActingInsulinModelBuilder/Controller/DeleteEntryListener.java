package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.view.View;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;

/**
 * Created by kbb12 on 23/02/2017.
 */
public class DeleteEntryListener implements View.OnClickListener {

    private LongActingInsulinReadWriteModel model;
    private Integer entryNumber;

    public DeleteEntryListener(LongActingInsulinReadWriteModel model,int entryNumber){
        this.model=model;
        this.entryNumber=entryNumber;
    }

    @Override
    public void onClick(View v) {
        model.setDoseToBeDeleted(entryNumber);
    }
}
