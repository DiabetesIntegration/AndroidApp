package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 27/03/2017.
 */

public class TimeSetListener implements TimePickerDialog.OnTimeSetListener {

        private AddFitnessReadWriteModel model;

        public TimeSetListener(AddFitnessReadWriteModel model){
            this.model=model;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            model.setTimeTaken(hourOfDay,minute);
        }
}
