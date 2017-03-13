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
    //private View v;

    public MealListController(IMealList model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
        //this.v = v;
    }


    /*
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }*/

    @Override
    public void onClick(View v) {
        //currentActivity.finish();
        model.setIngListView();
        model.setNewMeal();
        Intent ingredientIntent = new Intent(currentActivity, AddIngredientActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("tester", position + ".POSITIONMEALLISTCONTROLLER");
        model.setIngListView();
        model.setMealItem(position);
        model.getIngredientsForMeal();
        //model.setNewMeal(false);
        Intent ingredientIntent = new Intent(currentActivity, IngredientListActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(ingredientIntent);
    }
}
