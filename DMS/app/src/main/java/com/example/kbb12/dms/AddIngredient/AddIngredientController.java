package com.example.kbb12.dms.AddIngredient;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.kbb12.dms.CustomIngredient.AddCustomIngredientActivity;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientController implements View.OnClickListener {
    IAddIngredient model;
    private Activity currentActivity;

    public AddIngredientController(IAddIngredient model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {
        Log.i("tester", "YASSSSSSSSSSSS");
        model.newCustomIngredient();
        Log.i("tester", "22222222222222222");
        //currentActivity.finish();
        Intent ingredientIntent = new Intent(currentActivity, AddCustomIngredientActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);

    }
}
