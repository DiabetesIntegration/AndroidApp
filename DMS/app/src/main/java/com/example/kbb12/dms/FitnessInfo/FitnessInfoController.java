package com.example.kbb12.dms.fitnessInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.example.kbb12.dms.addFitness.AddFitnessActivity;
import com.example.kbb12.dms.enterWeight.EnterWeightActivity;

/**
 * Created by Garry on 07/03/2017.
 */

public class FitnessInfoController implements View.OnClickListener  {

    private IFitnessInfo model;
    private Activity currentActivity;

    public FitnessInfoController(IFitnessInfo model, Activity currentActivity){
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sprefs = currentActivity.getSharedPreferences("fitnessprefs", currentActivity.getApplicationContext().MODE_PRIVATE);
        double weight;
        try{
            weight = (double) sprefs.getFloat("weight", (float)0.0);
        } catch(NumberFormatException e){
            Intent intent = new Intent(currentActivity, EnterWeightActivity.class);
            currentActivity.startActivity(intent);
            return;
        }
        if (weight == 0.0){
            Intent intent = new Intent(currentActivity, EnterWeightActivity.class);
            currentActivity.startActivity(intent);
            return;
        }

        Intent addFitnessIntent = new Intent(currentActivity, AddFitnessActivity.class);
        currentActivity.startActivity(addFitnessIntent);
    }
}
