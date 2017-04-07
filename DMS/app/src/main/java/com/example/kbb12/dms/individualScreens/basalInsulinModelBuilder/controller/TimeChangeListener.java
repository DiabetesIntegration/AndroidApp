package com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.controller;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.kbb12.dms.individualScreens.basalInsulinModelBuilder.model.BasalInsulinReadWriteModel;

/**
 * Created by kbb12 on 01/02/2017.
 */
public class TimeChangeListener implements TimePickerDialog.OnTimeSetListener {

    private BasalInsulinReadWriteModel model;

    public TimeChangeListener(BasalInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        model.setHour(hourOfDay);
        model.setMinute(minute);
        model.deselectTime();
    }
}
