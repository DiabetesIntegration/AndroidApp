package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface AddIngredientMainModel {
    List<IIngredient> getSavedIngredients();
    void addIngredientToMeal(IIngredient ingredient);
    List<List<String>> getAllScanableItems();
}
