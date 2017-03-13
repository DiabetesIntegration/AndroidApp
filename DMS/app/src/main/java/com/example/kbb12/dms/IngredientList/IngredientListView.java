package com.example.kbb12.dms.IngredientList;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.CustomListView.CustomAdapter;
import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListView implements ModelObserver {
    private EditText mName;
    private ListView iList;
    private ImageButton addIng;
    private Button finishList;
    private CustomAdapter adapter;
    private IIngredientList model;


    public IngredientListView(EditText mealName, ListView currentIngredients, ImageButton anotherIngredient, Button completeMeal, CustomAdapter adapter, IIngredientList model) {
        mName = mealName;
        iList = currentIngredients;
        addIng = anotherIngredient;
        finishList = completeMeal;
        this.adapter = adapter;
        this.model = model;
    }



    @Override
    public void update() {
        //upate listView here
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        adapter.addAll(model.getIngredientsInMeal());

        mName.setText(model.getMealName());
    }
}
