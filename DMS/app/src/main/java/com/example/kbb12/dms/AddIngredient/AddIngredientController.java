package com.example.kbb12.dms.AddIngredient;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.CustomIngredient.AddCustomIngredientActivity;
import com.example.kbb12.dms.IngredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.MealCarbohydrateValue.MealCarbohydrateValueActivity;
import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientController implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {
    IAddIngredient model;
    private Activity currentActivity;

    public AddIngredientController(IAddIngredient model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addCustomIngredientButton):
                model.setNewIngredient();
                model.setAddIngredient(true);
                Intent ingredientIntent = new Intent(currentActivity, AddCustomIngredientActivity.class);
                currentActivity.startActivity(ingredientIntent);
                break;
            case (R.id.scanBarcodeButton):

                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.getSavedIngredient(model.getSavedIngredients().get(position));
        model.setAddIngredient(true);
        addSavedIngredient();
        model.itemSearch("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        model.itemSearch(s.toString());
    }

    public void addSavedIngredient() {
        Intent ingredientIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);
    }
}