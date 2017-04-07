package com.example.kbb12.dms.individualScreens.takeInsulin.controller;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadWriteModel;

/**
 * Created by kbb12 on 08/02/2017.
 */
public class DateSetListener implements DatePickerDialog.OnDateSetListener {

    private TakeInsulinReadWriteModel model;

    public DateSetListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        model.setDateTaken(dayOfMonth,monthOfYear,year);
        model.setTimeToChange(true);
    }
}
