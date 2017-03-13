package com.example.kbb12.dms.CustomIngredient;

import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientView implements ModelObserver {
    private EditText ingredientName, carbVal, packVal, sugarVal, weightVal;
    private ImageButton createCustom, cancelCustom;
    private IAddCustomIngredient model;

    public AddCustomIngredientView(EditText name, EditText carb, EditText packet, EditText sugar, EditText weight, IAddCustomIngredient model) {
        ingredientName = name;
        carbVal = carb;
        packVal = packet;
        sugarVal = sugar;
        weightVal = weight;
        this.model = model;
    }

    @Override
    public void update() {
    }
}
