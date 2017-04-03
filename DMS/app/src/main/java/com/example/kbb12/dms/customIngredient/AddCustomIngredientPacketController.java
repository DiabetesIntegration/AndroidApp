package com.example.kbb12.dms.customIngredient;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientPacketController implements TextWatcher {
    private IAddCustomIngredient model;
    private Activity currentActivity;
    private String packetEntry = "";

    public AddCustomIngredientPacketController(IAddCustomIngredient model, Activity currentActivity) {
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
        packetEntry = s.toString();
    }

    public String getPacketEntry() {
        return packetEntry;
    }
}