package com.example.kbb12.dms.addFitness.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.kbb12.dms.addFitness.IAddFitness;

/**
 * Created by Garry on 26/03/2017.
 */

public class ValidateAndSave implements View.OnClickListener {

    IAddFitness model;
    Activity activity;

    public ValidateAndSave(IAddFitness model, Activity activity){
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
        SharedPreferences spref = activity.getSharedPreferences("fitnessprefs", Context.MODE_PRIVATE);
        double weight = 0.0;
        try {
            weight = (double) spref.getFloat("weight", (float)0.0);
        } catch(NumberFormatException e){
            model.setError("You have not entered your weight");
            return;
        }
        if(weight==0.0){
            model.setError("You have not entered your weight.");
            return;
        }

        model.saveActivity();
        activity.finish();
    }
}
