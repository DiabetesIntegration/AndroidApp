package com.example.kbb12.dms.IngredientList;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.kbb12.dms.AddIngredient.AddIngredientActivity;
import com.example.kbb12.dms.CustomIngredient.AddCustomIngredientActivity;
import com.example.kbb12.dms.IngredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.MealAmount.MealAmountActivity;
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
        if(v.getId() == R.id.addAnotherIngredientButton) {
            model.setIngListView(true);
            anotherIngredientActivity();
        }
        else if(v.getId() == R.id.eatMealButton) {
            if(!model.getMealName().trim().equals("")) {
                if(model.getIngredientsInMeal().isEmpty()) {
                    Toast.makeText(currentActivity, "Error! There are no ingredients in the meal!", Toast.LENGTH_SHORT).show();
                }
                else {
                    model.createMeal();
                    nextActivity();
                    model.setIngListView(false);
                }
                //model.setMealName("");

            }
            else {
                Toast.makeText(currentActivity, "Error! Nothing was entered for the meal name!", Toast.LENGTH_SHORT).show();
            }
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
        //currentActivity.finish();
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
        Intent templateIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
