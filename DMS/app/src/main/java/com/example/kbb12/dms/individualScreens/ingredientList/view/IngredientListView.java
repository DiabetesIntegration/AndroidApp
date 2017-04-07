package com.example.kbb12.dms.individualScreens.ingredientList.view;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomAdapter;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.ingredientList.model.IngredientListReadModel;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListView extends MasterView implements ModelObserver {
    private CustomAdapter adapter;
    private IngredientListReadModel model;


    public IngredientListView(EditText mealName, CustomAdapter adapter, IngredientListReadModel model,
                              FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        mealName.setText(model.getMealName());
        this.adapter = adapter;
        this.model = model;
        update();
    }



    @Override
    public void update() {
        handleError(model.getError());
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        List<String> ingredientsStrings = new ArrayList<>();
        Double numCarbs;
        for(IIngredient ingredient:model.getIngredients()){
            numCarbs=(model.getAmountOf(ingredient)/100.0)*ingredient.getCarbsPerHundredG();
            ingredientsStrings.add(String.format("%s - %.2f of carbs",ingredient.getName(),numCarbs));
        }
        adapter.addAll(ingredientsStrings);
    }
}
