package com.example.kbb12.dms.addFitness.controller;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.kbb12.dms.addFitness.model.IAddFitness;

/**
 * Created by Garry on 27/03/2017.
 */

public class TimeSetListener implements TimePickerDialog.OnTimeSetListener {

        private IAddFitness model;

        public TimeSetListener(IAddFitness model){
            this.model=model;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            model.setTimeTaken(hourOfDay,minute);
        }
}
