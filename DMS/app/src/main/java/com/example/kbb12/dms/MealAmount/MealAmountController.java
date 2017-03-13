package com.example.kbb12.dms.MealAmount;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.MealList.MealListActivity;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountController implements View.OnClickListener, TextWatcher {
    private IMealAmount model;
    private Activity currentActivity;

    public MealAmountController(IMealAmount model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        if(model.getMealAmount().equals("")) {
            Toast.makeText(currentActivity, "Error! Nothing was entered for the amount of meal consumed!", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(model.getMealAmount()) > 100) {
            Toast.makeText(currentActivity, "Error! The percentage of meal consumed cannot exceed 100%!", Toast.LENGTH_SHORT).show();
        }
        else {
            model.setMealCarbs();
            nextActivity();
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
        model.setMealAmount(s.toString());
    }

    public void nextActivity(){
        //currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, MealListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
