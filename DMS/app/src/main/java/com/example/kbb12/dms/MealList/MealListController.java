package com.example.kbb12.dms.MealList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.AddIngredient.AddIngredientActivity;
import com.example.kbb12.dms.IngredientList.IngredientListActivity;
import com.example.kbb12.dms.Template.ITemplateModel;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListController implements View.OnClickListener, AdapterView.OnItemClickListener {
    private IMealList model;
    private Activity currentActivity;

    public MealListController(IMealList model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }



    @Override
    public void onClick(View v) {
        model.setIngListView();
        model.setNewMeal();
        Intent ingredientIntent = new Intent(currentActivity, AddIngredientActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setIngListView();
        model.setMealItem(position);
        model.getIngredientsForMeal();
        Intent ingredientIntent = new Intent(currentActivity, IngredientListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);
    }
}
