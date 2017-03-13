package com.example.kbb12.dms.MealAmount;

import android.widget.EditText;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountView implements ModelObserver {

    private EditText amount;
    private IMealAmount model;


    public MealAmountView(EditText entry, IMealAmount model) {
        amount = entry;
        this.model = model;
    }


    @Override
    public void update() {
    }
}
