package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.kbb12.dms.StartUp.UserModel;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class AddCustomIngredientPacketWeightController implements TextWatcher {
    private IAddCustomIngredient model;
    private Activity currentActivity;

    public AddCustomIngredientPacketWeightController(IAddCustomIngredient model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //Log.i("tester", "YASSS 4");
        model.setCustomPacketWeightVal(s.toString());
    }
}
