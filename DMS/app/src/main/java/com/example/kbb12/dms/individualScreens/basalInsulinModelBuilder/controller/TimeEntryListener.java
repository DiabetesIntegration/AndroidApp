package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class TimeEntryListener implements AdapterView.OnClickListener{

    private int entryNumber;
    private BasalInsulinReadWriteModel model;

    public TimeEntryListener(int entryNumber, BasalInsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }


    @Override
    public void onClick(View v) {
        model.setSelectedTime(entryNumber);
    }
}
