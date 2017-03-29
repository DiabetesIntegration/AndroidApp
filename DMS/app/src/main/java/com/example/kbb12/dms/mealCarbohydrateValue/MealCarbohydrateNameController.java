package com.example.kbb12.dms.mealCarbohydrateValue;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateNameController implements TextWatcher {
    private IMealCarbohydrateValue model;
    private Activity currentActivity;
    private String nameEntry="";

    public MealCarbohydrateNameController(IMealCarbohydrateValue model, Activity currentActivity) {
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
        nameEntry = s.toString();
    }


    public String getNameEntry() {
        return nameEntry;
    }
}
