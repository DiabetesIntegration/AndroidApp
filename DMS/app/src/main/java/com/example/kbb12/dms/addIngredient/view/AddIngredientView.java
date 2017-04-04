package com.example.kbb12.dms.addIngredient.view;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.addIngredient.model.IAddIngredient;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientView implements ModelObserver {

    private ArrayAdapter<String> adapter;
    private IAddIngredient model;

    public AddIngredientView(ArrayAdapter adapter, IAddIngredient model) {
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

