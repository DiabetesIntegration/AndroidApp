package com.example.kbb12.dms.individualScreens.fitnessInfo.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.addFitness.AddFitnessActivity;
import com.example.kbb12.dms.individualScreens.enterWeight.EnterWeightActivity;
import com.example.kbb12.dms.individualScreens.fitnessInfo.model.IFitnessInfo;

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
        double weight = model.getWeight();
        if (weight == 0.0){
            Intent intent = new Intent(currentActivity, EnterWeightActivity.class);
            currentActivity.startActivity(intent);
            return;
        }

        Intent addFitnessIntent = new Intent(currentActivity, AddFitnessActivity.class);
        currentActivity.startActivity(addFitnessIntent);
    }
}
