package com.example.kbb12.dms.AddIngredient;

import android.widget.ImageButton;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientView implements ModelObserver {

    private ImageButton addCustom;
    private IAddIngredient model;

    public AddIngredientView(ImageButton b, IAddIngredient model) {
        addCustom = b;
        this.model = model;
    }

    @Override
    public void update() {
    }
}

