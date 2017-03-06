package com.example.kbb12.dms.IngredientAmount;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientsAmountView implements ModelObserver {
    private ToggleButton weightPrecentage;
    private EditText amount;
    private TextView unit;
    private IIngredientsAmount model;


    public IngredientsAmountView(ToggleButton b, EditText entry, TextView units, IIngredientsAmount model) {
        weightPrecentage = b;
        amount = entry;
        unit = units;
        this.model = model;
    }


    @Override
    public void update() {
        unit.setText(model.getUnits());
    }
}
