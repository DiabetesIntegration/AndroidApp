package com.example.kbb12.dms.IngredientAmount;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.kbb12.dms.AddIngredient.AddIngredientActivity;
import com.example.kbb12.dms.IngredientList.IngredientListActivity;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.Template.TemplateActivity;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientsAmountController implements View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private IIngredientsAmount model;
    private Activity currentActivity;
    private String amount = "";

    public IngredientsAmountController(IIngredientsAmount model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }


    @Override
    public void onClick(View v) {
        if(amount.isEmpty()) {
            model.getIngAmountErrorMessage("Error! Nothing was entered for the ingredient amount!");
        }
        else {
            try {
                int a = Integer.parseInt(amount);
                if(model.getUnits().equals("%") && a > 100) {
                    model.getIngAmountErrorMessage("Error! The percentage eaten cannot exceed 100%!");
                }
                else {
                    model.setCarbValOfIngredient(amount);
                    if(!model.ingredientExists()) {
                        model.addNewIngredient();
                        model.setIngredientExists(true);
                    }
                    model.setIngredientListView();
                    nextActivity();
                }
            }
            catch(NumberFormatException e) {
                model.getIngAmountErrorMessage("Error! The ingredient amount entered is not an integer!");
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
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, IngredientListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
