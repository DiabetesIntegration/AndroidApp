package com.example.kbb12.dms.addFitness.controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.addFitness.model.IAddFitness;

/**
 * Created by Garry on 26/03/2017.
 */

public class ActivityTypeListener implements AdapterView.OnItemSelectedListener {

    IAddFitness model;

    public ActivityTypeListener(IAddFitness model){
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
