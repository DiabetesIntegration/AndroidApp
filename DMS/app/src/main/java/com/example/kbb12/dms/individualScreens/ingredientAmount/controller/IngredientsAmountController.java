package com.example.kbb12.dms.individualScreens.ingredientAmount.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.example.kbb12.dms.individualScreens.ingredientAmount.model.IngredientsAmountReadWriteModel;
import com.example.kbb12.dms.individualScreens.ingredientList.IngredientListActivity;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientsAmountController implements View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private IngredientsAmountReadWriteModel model;
    private Activity currentActivity;
    private String amount = "";

    public IngredientsAmountController(IngredientsAmountReadWriteModel model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }


    @Override
    public void onClick(View v) {
        if(amount.isEmpty()) {
            model.setError("Error! Nothing was entered for the ingredient amount!");
        }
        else {
            try {
                int a = Integer.parseInt(amount);
                model.saveAmount(a);
                nextActivity();
            }
            catch(NumberFormatException e) {
                model.setError("Error! The ingredient amount entered is not an integer!");
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
        amount = s.toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            model.setUnits("g");
        }
        else {
            model.setUnits("%");
        }
    }

    public void nextActivity(){
        Intent templateIntent = new Intent(currentActivity, IngredientListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
