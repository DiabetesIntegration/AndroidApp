package com.example.kbb12.dms.ingredientList;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.addIngredient.AddIngredientActivity;
import com.example.kbb12.dms.ingredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.mealAmount.MealAmountActivity;
import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListController implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {
    private IIngredientList model;
    private Activity currentActivity;

    public IngredientListController(IIngredientList model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addAnotherIngredientButton):
                model.setIngListView(true);
                anotherIngredientActivity();
                break;
            case (R.id.eatMealButton):
                if(model.getMealName().isEmpty()) {
                    model.getIngListErrorMessage("Error! No meal name was entered!");
                }
                else if(model.getIngredientsInMeal().isEmpty()) {
                    //no ingredients in meal, all have been deleted
                    model.getIngListErrorMessage("Error! There are no ingredients in the meal!");
                }
                else if(!model.checkMealName()) {
                    //meal name already exists
                    model.getIngListErrorMessage("Error! This meal name already exists! Please choose another name!");
                }
                else {
                    model.setIngredientsForMeal();
                    model.setTotalCarbs();

                    nextActivity();
                    model.setIngListView(false);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setIngredientItem(position);
        editIngredientActivity();
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

    public void anotherIngredientActivity(){
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, AddIngredientActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }

    public void nextActivity(){
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, MealAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }

    public void editIngredientActivity() {
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
