package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientController implements View.OnClickListener, TextWatcher {
    private IAddCustomIngredient model;
    private Activity currentActivity;

    public AddCustomIngredientController(IAddCustomIngredient model, Activity currentActivity) {
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
                Log.i("tester", "IT WORKSSSSSS!!!!");
            }
        }
        else if(v.getId() == R.id.cancelCustomButton) {
            currentActivity.finish();
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
        if() {
           model.setItemName(s.toString());
        }
        else if() {
            model.setCarbVal(s.toString());
        }
        else if() {
            model.setPacketVal(s.toString());
        }
        else {
            model.setSugarVal(s.toString());
        }
    }
}
