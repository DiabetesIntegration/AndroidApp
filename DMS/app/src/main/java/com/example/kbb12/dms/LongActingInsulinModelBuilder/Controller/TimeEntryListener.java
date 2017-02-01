package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class TimeEntryListener implements AdapterView.OnClickListener{

    private int entryNumber;
    private LongActingInsulinReadWriteModel model;

    public TimeEntryListener(int entryNumber, LongActingInsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }


    @Override
    public void onClick(View v) {
        model.setSelectedTime(entryNumber);
    }
}
