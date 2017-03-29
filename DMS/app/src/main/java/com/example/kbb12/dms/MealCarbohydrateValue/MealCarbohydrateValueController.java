package com.example.kbb12.dms.MealCarbohydrateValue;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateValueController implements TextWatcher {
    private IMealCarbohydrateValue model;
    private Activity currentActivity;
    private String valueEntry = "";

    public MealCarbohydrateValueController(IMealCarbohydrateValue model, Activity currentActivity) {
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
        valueEntry = s.toString();
    }


    public String getValueEntry() {
        return valueEntry;
    }
}
