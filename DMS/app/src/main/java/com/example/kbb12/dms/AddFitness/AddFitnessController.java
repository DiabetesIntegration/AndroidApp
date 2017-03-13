package com.example.kbb12.dms.AddFitness;

import android.app.Activity;

/**
 * Created by Garry on 13/03/2017.
 */

public class AddFitnessController {

    IAddFitness model;
    Activity currentActivity;

    public AddFitnessController(IAddFitness model, Activity currentActivity){
        this.model = model;
        this.currentActivity = currentActivity;
    }
}
