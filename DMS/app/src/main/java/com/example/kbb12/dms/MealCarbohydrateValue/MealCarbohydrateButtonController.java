package com.example.kbb12.dms.MealCarbohydrateValue;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.MealAmount.MealAmountActivity;
import com.example.kbb12.dms.MealList.MealListActivity;
import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateButtonController implements View.OnClickListener {
    private IMealCarbohydrateValue model;
    private Activity currentActivity;
    private MealCarbohydrateNameController name;
    private MealCarbohydrateValueController value;

    public MealCarbohydrateButtonController(IMealCarbohydrateValue model, Activity currentActivity, MealCarbohydrateNameController name, MealCarbohydrateValueController value) {
        this.model = model;
        this.currentActivity = currentActivity;
        this.name = name;
        this.value = value;
    }

    @Override
    public void onClick(View v) {
        if(name.getNameEntry().isEmpty()) {
            model.getMealCarbohydrateErrorMessage("Error! Nothing was entered for the meal name!");
        }
        else if(value.getValueEntry().isEmpty()) {
            model.getMealCarbohydrateErrorMessage("Error! Nothing was entered for the amount of carbohydrate!");
        }
        else {
            try {
                Integer.parseInt(value.getValueEntry());
                switch(v.getId()) {
                    case (R.id.saveCarbMealButton) :
                        model.setStraightCarbs(true);
                        model.setCarbMealName(name.getNameEntry());
                        model.setCarbMealValue(value.getValueEntry());
                        if(model.addCarbMeal()) {
                            model.addNewCarbMeal();
                            model.notIngredientList(false);
                            nextActivity();
                        }
                        else {
                            //error, name for meal already exists
                            model.getMealCarbohydrateErrorMessage("Error! The entered meal name already exists! Please enter a new meal name!");
                        }
                        break;
                    case (R.id.eatCarbMealButton) :
                        //send data to greg
                        break;
                }

            }
            catch(NumberFormatException e) {
                model.getMealCarbohydrateErrorMessage("Error! The entered carbohydrate is not an integer!");
            }
        }

    }

    public void nextActivity() {
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, MealListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
