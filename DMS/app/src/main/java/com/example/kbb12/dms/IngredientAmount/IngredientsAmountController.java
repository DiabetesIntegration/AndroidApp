package com.example.kbb12.dms.IngredientAmount;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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

    public IngredientsAmountController(IIngredientsAmount model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ingredientAmountBackButton) {
            model.setIngredientAmount("");
            currentActivity.finish();
        }
        else if(v.getId() == R.id.ingredientAmountConfirmButton) {
            if(model.getIngredientAmount().equals("")) {
                Toast.makeText(currentActivity, "Error! Nothing was entered for the amount of ingredient!", Toast.LENGTH_SHORT).show();
            }
            else {
                if(!model.isWeight()) {
                    if(Integer.parseInt(model.getIngredientAmount()) > 100) {
                        Toast.makeText(currentActivity, "Error! The percentage of ingredient used cannot exceed 100%!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        nextActivity();
                    }
                }
                else {
                    nextActivity();
                }
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
        model.setIngredientAmount(s.toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            model.setUnits("g");
            model.changeUnit(true);
        }
        else {
            model.setUnits("%");
            model.changeUnit(false);
        }
    }

    public void nextActivity(){
        Intent templateIntent = new Intent(currentActivity, IngredientListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
