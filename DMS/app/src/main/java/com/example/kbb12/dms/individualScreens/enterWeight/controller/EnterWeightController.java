package com.example.kbb12.dms.individualScreens.enterWeight.controller;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.example.kbb12.dms.individualScreens.enterWeight.model.EnterWeightReadWriteModel;


/**
 * Created by Garry on 29-Mar-17.
 */

public class EnterWeightController  implements View.OnClickListener  {

    private EditText weighttext;
    private Activity currentactivity;
    private EnterWeightReadWriteModel model;

    public EnterWeightController(Activity activity, EditText weighttext, EnterWeightReadWriteModel model){
        this.currentactivity=activity;
        this.weighttext=weighttext;
        this.model=model;
    }

    @Override
    public void onClick(View view) {
        try{
            double weight = Double.parseDouble(weighttext.getText().toString());
            model.setWeight(weight);
        } catch(NumberFormatException e){
            model.setError("Please enter a numeric value");
            return;
        }
        currentactivity.finish();
    }
}
