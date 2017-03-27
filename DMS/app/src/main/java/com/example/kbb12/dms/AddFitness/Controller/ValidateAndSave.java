package com.example.kbb12.dms.AddFitness.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.AddFitness.IAddFitness;

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
            Toast.makeText(activity, "Please enter minutes less than 60.", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences spref = activity.getSharedPreferences("fitnessprefs", Context.MODE_PRIVATE);
        double weight = 0.0;
        try {
            weight = Double.longBitsToDouble(spref.getLong("weight", Double.doubleToLongBits(0.0)));
        } catch(NumberFormatException e){
            Toast.makeText(activity, "You have not entered your weight", Toast.LENGTH_SHORT).show();
            return;
        }
        if(weight==0.0){
            Toast.makeText(activity, "You have not entered your weight", Toast.LENGTH_SHORT).show();
            return;
        }

        model.saveActivity(activity.getApplicationContext(), model.getActivityType(), model.getYearTaken(), model.getMonthTaken(), model.getDayTaken(), model.getHourTaken(), model.getMinuteTaken(), model.getDurhours(), model.getDurMins(), weight);
    }
}
