package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientSugarController implements TextWatcher {
    private IAddCustomIngredient model;
    private Activity currentActivity;

    public AddCustomIngredientSugarController(IAddCustomIngredient model, Activity currentActivity) {
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
        model.setSugarVal(s.toString());
    }
}
