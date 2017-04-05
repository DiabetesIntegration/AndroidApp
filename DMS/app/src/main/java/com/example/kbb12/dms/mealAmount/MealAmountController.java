package com.example.kbb12.dms.mealAmount;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.kbb12.dms.mealList.MealListActivity;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountController implements View.OnClickListener, TextWatcher {
    private IMealAmount model;
    private Activity currentActivity;
    private String amountEntry = "";

    public MealAmountController(IMealAmount model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        if(amountEntry.trim().isEmpty()) {
            model.getMealAmountErrorMessage("Error! Nothing was entered for the meal amount!");
        }
        else {
            try {
                int a = Integer.parseInt(amountEntry);
                if(a > 100) {
                    model.getMealAmountErrorMessage("Error! The meal amount cannot exceed 100%!");
                }
                else {
                    switch(v.getId()) {
                        case (R.id.saveMealButton) :
                            addNewMeal();
                            nextActivity();
                            break;
                        case (R.id.eatMealButton) :
                            addNewMeal();
                            //TODO
                            //model.addNewMealDateCarb(model.mealToEat().getCarbsEaten());
                            currentActivity.finish();
                            Intent templateIntent = new Intent(currentActivity, TakeInsulin.class);
                            //Launches the next activity.
                            currentActivity.startActivity(templateIntent);
                            currentActivity.finish();
                            //go to gregs activity
                            //add to database - date/time and carb value
                            break;
                    }
                }
            }
            catch(NumberFormatException e) {
                model.getMealAmountErrorMessage("Error! The meal amount must be an integer!");
            }
        }
    }

    public void addNewMeal() {
        model.setMealCarbAmount(amountEntry);
        model.saveNewIngredients();
        if(!model.mealExists()) {
            model.addNewMeal();
        }
        else {
            model.editMeal();
        }
        model.setMealListView();
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

    public void nextActivity(){
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, MealListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
