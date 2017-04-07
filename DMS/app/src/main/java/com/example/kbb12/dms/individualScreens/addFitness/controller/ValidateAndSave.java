package com.example.kbb12.dms.individualScreens.addFitness.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.kbb12.dms.individualScreens.addFitness.model.AddFitnessReadWriteModel;

/**
 * Created by Garry on 26/03/2017.
 */

public class ValidateAndSave implements View.OnClickListener {

    AddFitnessReadWriteModel model;
    Activity activity;

    public ValidateAndSave(AddFitnessReadWriteModel model, Activity activity){
        this.model=model;
        this.activity=activity;
    }

    @Override
    public void onClick(View view) {
        if(model.getDurMins()>59){
            model.setDurMins(0);
            model.setError("Please enter minutes less than 60.");
            return;
        }
        if(model.getDurhours() == 0 && model.getDurMins() == 0){
            model.setError("Please enter the duration of activity.");
            return;
        }
        model.saveActivity();
        activity.finish();
    }
}
