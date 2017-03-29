package com.example.kbb12.dms.addIngredient;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientView implements ModelObserver {

    private ImageButton addCustom;
    private EditText search;
    private ListView savedIng;
    private ArrayAdapter<String> adapter;
    private IAddIngredient model;

    public AddIngredientView(ImageButton b, EditText search, ListView savedIng, ArrayAdapter adapter, IAddIngredient model) {
        addCustom = b;
        this.search = search;
        this.savedIng = savedIng;
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

