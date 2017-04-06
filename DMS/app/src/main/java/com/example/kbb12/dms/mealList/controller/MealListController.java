package com.example.kbb12.dms.mealList.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.addIngredient.AddIngredientActivity;
import com.example.kbb12.dms.ingredientList.IngredientListActivity;
import com.example.kbb12.dms.mealAmount.MealAmountActivity;
import com.example.kbb12.dms.mealCarbohydrateValue.MealCarbohydrateValueActivity;
import com.example.kbb12.dms.mealList.model.MealListReadWriteModel;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListController implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MealListReadWriteModel model;
    private Activity currentActivity;

    public MealListController(MealListReadWriteModel model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addCustomCarbMealButton) :
            case (R.id.addCustomCarbMealText):
                model.setNewMeal();
                Intent mealCarbIntent = new Intent(currentActivity, MealCarbohydrateValueActivity.class);
                currentActivity.startActivity(mealCarbIntent);
                break;
            case (R.id.addMealButton) :
            case (R.id.addMealText):
                model.setNewMeal();
                Intent ingredientIntent = new Intent(currentActivity, AddIngredientActivity.class);
                //Launches the next activity.
                currentActivity.startActivity(ingredientIntent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.selectMeal(position);
        if(!model.isSelectedMealCustom()) {
            Intent ingredientIntent = new Intent(currentActivity, IngredientListActivity.class);
            //Launches the next activity.
            currentActivity.startActivity(ingredientIntent);
        }
        else {
            Intent templateIntent = new Intent(currentActivity, MealCarbohydrateValueActivity.class);
            //Launches the next activity.
            currentActivity.startActivity(templateIntent);
            //go to gregs screen
        }
    }
}
