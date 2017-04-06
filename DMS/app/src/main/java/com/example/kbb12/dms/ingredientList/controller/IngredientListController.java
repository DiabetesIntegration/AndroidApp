package com.example.kbb12.dms.ingredientList.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.addIngredient.AddIngredientActivity;
import com.example.kbb12.dms.customIngredient.AddCustomIngredientActivity;
import com.example.kbb12.dms.ingredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.ingredientList.model.IngredientListReadWriteModel;
import com.example.kbb12.dms.mealAmount.MealAmountActivity;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.mealList.MealListActivity;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListController implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {
    private IngredientListReadWriteModel model;
    private Activity currentActivity;

    public IngredientListController(IngredientListReadWriteModel model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        Intent nextIntent=new Intent(currentActivity, MealListActivity.class);
        switch(v.getId()) {
            case (R.id.addAnotherIngredientButton):
            case(R.id.addAnotherIngredientText):
                model.newActiveIngredient();
                nextIntent=new Intent(currentActivity, AddIngredientActivity.class);
                break;
            case (R.id.eatMealButton):
                nextIntent=new Intent(currentActivity, MealAmountActivity.class);
            case (R.id.saveMealButton):
                if(model.getMealName().isEmpty()) {
                    model.setError("Error! No meal name was entered!");
                    return;
                }
                else if(model.getIngredients().isEmpty()) {
                    //no ingredients in meal, all have been deleted
                    model.setError("Error! There are no ingredients in the meal!");
                    return;
                }
                else if(!model.checkMealName()) {
                    //meal name already exists
                    model.setError("Error! This meal name already exists! Please choose another name!");
                    return;
                }
                else {
                    model.saveMeal();
                }
                break;
        }
        currentActivity.startActivity(nextIntent);
        currentActivity.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setEditableIngredientPosition(position);
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, AddCustomIngredientActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        model.setMealName(s.toString());
    }

}
