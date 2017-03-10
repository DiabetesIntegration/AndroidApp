package com.example.kbb12.dms.takeInsulin.controller;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadWriteModel;


/**
 * Created by kbb12 on 01/02/2017.
 */
public class TimeSetListener implements TimePickerDialog.OnTimeSetListener {

    private TakeInsulinReadWriteModel model;

    public TimeSetListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        model.setTimeTaken(hourOfDay,minute);
    }
}
