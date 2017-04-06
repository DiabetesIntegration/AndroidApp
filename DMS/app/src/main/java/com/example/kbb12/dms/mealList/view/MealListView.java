package com.example.kbb12.dms.mealList.view;

import android.widget.TextView;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.mealList.model.MealListReadWriteModel;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListView implements ModelObserver{

    private TextView noMeals;
    private CustomAdapter adapter;
    private MealListReadWriteModel model;

    public MealListView(TextView t, CustomAdapter adapter, MealListReadWriteModel model) {
        noMeals = t;
        this.adapter = adapter;
        adapter.addAll(model.getSavedMeals());
        this.model = model;
        update();
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
