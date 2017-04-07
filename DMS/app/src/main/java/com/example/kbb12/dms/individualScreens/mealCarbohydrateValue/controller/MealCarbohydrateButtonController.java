package com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.TextWatcher;
import android.view.View;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.BasicTextWatcher;
import com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.model.MealCarbohydrateReadWriteModel;
import com.example.kbb12.dms.individualScreens.mealList.MealListActivity;
import com.example.kbb12.dms.individualScreens.takeInsulin.TakeInsulin;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateButtonController implements View.OnClickListener {
    private MealCarbohydrateReadWriteModel model;
    private Activity currentActivity;
    private BasicTextWatcher name;
    private BasicTextWatcher value;

    public MealCarbohydrateButtonController(MealCarbohydrateReadWriteModel model,
                                            Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
        this.name = new BasicTextWatcher();
        this.value = new BasicTextWatcher();
    }
    
    public TextWatcher getMealCarbohydrateNameController(){
        return name;
    }

    public TextWatcher getMealCarbohydrateValueController(){
        return value;
    }

    @Override
    public void onClick(View v) {
        Intent nextActivity=null;
        if(name.getEntry().isEmpty()) {
            model.setError("Error! Nothing was entered for the meal name!");
        }
        else if(value.getEntry().isEmpty()) {
            model.setError("Error! Nothing was entered for the amount of carbohydrate!");
        }
        else {
            try {
                int valueInt=Integer.parseInt(value.getEntry());
                if(!model.isNameTaken(name.getEntry())) {
                    model.addNewCarbMeal(name.getEntry(), valueInt);
                }else {
                        //error, name for meal already exists
                    model.setError("Error! The entered meal name already exists! Please enter a new meal name!");
                    return;
                }
                switch(v.getId()) {
                    case (R.id.saveCarbMealButton) :
                        nextActivity = new Intent(currentActivity, MealListActivity.class);
                        break;
                    case (R.id.eatCarbMealButton) :
                        model.eatCarbs(valueInt);
                        nextActivity = new Intent(currentActivity, TakeInsulin.class);
                        break;
                }
                //Launches the next activity.
                currentActivity.startActivity(nextActivity);
                currentActivity.finish();
            }
            catch(NumberFormatException e) {
                model.setError("Error! The entered carbohydrate is not an integer!");
            }
        }

    }
}
