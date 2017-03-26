package com.example.kbb12.dms.AddFitness.Controller;

import android.app.Activity;
import android.view.View;

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

    }
}
