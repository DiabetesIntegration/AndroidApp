package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.app.IntentService;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
            if(!model.checkEntry(model.getItemName())) {
                Toast.makeText(currentActivity, "Error! No name was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            }
            else if(!model.checkEntry(model.getCarbVal())) {
                Toast.makeText(currentActivity, "Error! No carb value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            }
            else if(!model.checkEntry(model.getPacketVal())) {
                Toast.makeText(currentActivity, "Error! No packet value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            }
            else if(!model.checkEntry(model.getSugarVal())) {
                Toast.makeText(currentActivity, "Error! No sugar value was entered for the ingredient!", Toast.LENGTH_SHORT).show();
            }
            else {
                if(Integer.parseInt(model.getCarbVal()) > Integer.parseInt(model.getPacketVal())) {
                    Toast.makeText(currentActivity, "Error! The carb value cannot be greater than the packet value!", Toast.LENGTH_SHORT).show();
                }
                if(Integer.parseInt(model.getSugarVal()) > Integer.parseInt(model.getCarbVal())) {
                    Toast.makeText(currentActivity, "Error! The sugar value cannot be greater than the carb value!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(v.getId() == R.id.cancelCustomButton) {
            model.setItemName("");
            model.setCarbVal("");
            model.setPacketVal("");
            model.setSugarVal("");
            currentActivity.finish();
        }

    }
}
