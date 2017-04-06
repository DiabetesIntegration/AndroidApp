package com.example.kbb12.dms.mealAmount.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.kbb12.dms.mealAmount.model.MealAmountReadWriteModel;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountController implements View.OnClickListener, TextWatcher {
    private MealAmountReadWriteModel model;
    private Activity currentActivity;
    private String amountEntry = "";

    public MealAmountController(MealAmountReadWriteModel model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        if(amountEntry.trim().isEmpty()) {
            model.setError("Error! Nothing was entered for the meal amount!");
        }
        else {
            try {
                int a = Integer.parseInt(amountEntry);
                if(a > 100) {
                    model.setError("Error! The meal amount cannot exceed 100%!");
                }
                else {
                    model.eatCurrentMeal(a);
                    currentActivity.finish();
                    Intent templateIntent = new Intent(currentActivity, TakeInsulin.class);
                    //Launches the next activity.
                    currentActivity.startActivity(templateIntent);
                    currentActivity.finish();
                }
            }
            catch(NumberFormatException e) {
                model.setError("Error! The meal amount must be an integer!");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        amountEntry = s.toString();
    }

}
