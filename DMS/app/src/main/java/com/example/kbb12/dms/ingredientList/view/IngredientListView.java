package com.example.kbb12.dms.ingredientList.view;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.ingredientList.model.IngredientListReadWriteModel;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListView extends MasterView implements ModelObserver {
    private EditText mName;
    private CustomAdapter adapter;
    private IngredientListReadWriteModel model;


    public IngredientListView(EditText mealName, CustomAdapter adapter, IngredientListReadWriteModel model,
                              FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        mName = mealName;
        this.adapter = adapter;
        this.adapter.addAll(model.getIngredientsInMeal());
        this.model = model;
        adapter.addAll(model.getIngredientsInMeal());
    }



    @Override
    public void update() {
        handleError(model.getError());

        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        adapter.addAll(model.getIngredientsInMeal());

        mName.setText(model.getMealName());

    }
}
