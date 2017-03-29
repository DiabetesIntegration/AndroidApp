package com.example.kbb12.dms.enterWeight.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import com.example.kbb12.dms.enterWeight.IEnterWeight;


/**
 * Created by Garry on 29-Mar-17.
 */

public class EnterWeightController  implements View.OnClickListener  {

    private EditText weighttext;
    private Activity currentactivity;
    private IEnterWeight model;

    public EnterWeightController(Activity activity, EditText weighttext, IEnterWeight model){
        this.currentactivity=activity;
        this.weighttext=weighttext;
        this.model=model;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor spredit = currentactivity.getApplication().getSharedPreferences("fitnessprefs", Context.MODE_PRIVATE).edit();
        try{
            double weight = Double.parseDouble(weighttext.getText().toString());
            spredit.putFloat("weight", (float) weight);
            spredit.apply();
        } catch(NumberFormatException e){
            model.setError("Please enter a numeric value");
            return;
        }
        currentactivity.finish();
    }
}
