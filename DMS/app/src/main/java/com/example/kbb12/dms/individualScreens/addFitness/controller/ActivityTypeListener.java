package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 26/03/2017.
 */

public class ActivityTypeListener implements AdapterView.OnItemSelectedListener {

    AddFitnessReadWriteModel model;

    public ActivityTypeListener(AddFitnessReadWriteModel model){
        this.model = model;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        switch(pos){
            case 0:
                model.setActivityType("Walking");
                break;
            case 1:
                model.setActivityType("Running");
                break;
            case 2:
                model.setActivityType("Cycling");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
