package com.example.kbb12.dms.addFitness.controller;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.example.kbb12.dms.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 26/03/2017.
 */

public class DateSetListener implements DatePickerDialog.OnDateSetListener {

    private AddFitnessReadWriteModel model;

    public DateSetListener(AddFitnessReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        model.setDateTaken(year, (month-1), day);
        model.setActTimeToChange(true);
    }
}
