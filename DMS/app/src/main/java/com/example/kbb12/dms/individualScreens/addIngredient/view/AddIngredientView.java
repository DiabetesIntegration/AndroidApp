package com.example.kbb12.dms.individualScreens.addIngredient.view;

import android.widget.ArrayAdapter;

import com.example.kbb12.dms.individualScreens.addIngredient.model.AddIngredientReadWriteModel;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientView implements ModelObserver {

    private ArrayAdapter<String> adapter;
    private AddIngredientReadWriteModel model;

    public AddIngredientView(ArrayAdapter adapter, AddIngredientReadWriteModel model) {
        this.adapter = adapter;
        if(!model.getSavedIngredients().isEmpty()) {
            adapter.addAll(model.getSavedIngredients());
        }
        this.model = model;
    }

    @Override
    public void update() {
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        adapter.addAll(model.getSavedIngredients());
    }
}

