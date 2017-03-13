package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.IngredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientButtonController implements View.OnClickListener {
    private IAddCustomIngredient model;
    private Activity currentActivity;

    public AddCustomIngredientButtonController(IAddCustomIngredient model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addCustomButton) {
            //Log.i("tester", model.getCustomIngredient().getNutritionalValues().get(0) + "," + model.getCustomIngredient().getNutritionalValues().get(1) + "," + model.getCustomIngredient().getNutritionalValues().get(2) + "," + model.getCustomIngredient().getNutritionalValues().get(3) + "!" );
            if (!model.checkEntry(model.getCustomIngredient().getIngredientName())) {
                Toast.makeText(currentActivity, "Error! No name was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            } else if (!model.checkEntry(model.getCustomIngredient().getNutritionalValues().get(0))) {
                Toast.makeText(currentActivity, "Error! No carb value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            } else if (!model.checkEntry(model.getCustomIngredient().getNutritionalValues().get(1))) {
                Toast.makeText(currentActivity, "Error! No packet value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            } else if (!model.checkEntry(model.getCustomIngredient().getNutritionalValues().get(2))) {
                Toast.makeText(currentActivity, "Error! No sugar value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            } else if (!model.checkEntry(model.getCustomIngredient().getNutritionalValues().get(3))) {
                Toast.makeText(currentActivity, "Error! No value was entered for the packet weight!", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(model.getCustomIngredient().getNutritionalValues().get(0)) > Integer.parseInt(model.getCustomIngredient().getNutritionalValues().get(1))) {
                Toast.makeText(currentActivity, "Error! The carb value cannot be greater than the packet value!", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(model.getCustomIngredient().getNutritionalValues().get(2)) > Integer.parseInt(model.getCustomIngredient().getNutritionalValues().get(0))) {
                Toast.makeText(currentActivity, "Error! The sugar value cannot be greater than the carb value!", Toast.LENGTH_SHORT).show();
            } else {
                nextActivity();
            }
        }
        else if(v.getId() == R.id.cancelCustomButton) {
            //model.setItemName("");
            //model.setCarbVal("");
            //model.setPacketVal("");
            //model.setSugarVal("");
            currentActivity.finish();
        }

    }

    public void nextActivity(){
        //currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
