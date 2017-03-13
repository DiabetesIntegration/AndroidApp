package com.example.kbb12.dms.MealList;

import android.media.Image;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.CustomListView.CustomAdapter;
import com.example.kbb12.dms.StartUp.ModelObserver;
import com.example.kbb12.dms.Template.ITemplateModel;

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
        this.model = model;
    }

    @Override
    public void update() {
        noMeals.setText(model.setEmptyString());
        if(noMeals.getText().equals("")) {
            if(!adapter.isEmpty()) {
                adapter.clear();
            }
            adapter.addAll(model.getSavedMeals());

        }
        else {
            adapter.clear();
        }
    }
}
