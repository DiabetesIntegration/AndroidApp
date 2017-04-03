package com.example.kbb12.dms.mealList;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListView implements ModelObserver{

    private TextView noMeals;
    private ImageButton addMeal;
    private ListView meals;
    private CustomAdapter adapter;
    private IMealList model;

    public MealListView(TextView t, ListView l, ImageButton b, CustomAdapter adapter, IMealList model) {
        noMeals = t;
        addMeal = b;
        meals = l;
        this.adapter = adapter;
        adapter.addAll(model.getSavedMeals());
        this.model = model;
    }

    @Override
    public void update() {
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        if(model.getSavedMeals().isEmpty()) {
            noMeals.setText("No saved meals yet");
        }
        else {
            noMeals.setText("");
            adapter.addAll(model.getSavedMeals());
        }
    }
}
