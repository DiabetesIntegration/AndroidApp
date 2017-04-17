package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;

/**
 * Created by kbb12 on 06/04/2017.
 */

public interface CustomIngredientMainModel {
    void saveIngredient(IIngredient ingredient);
    IIngredient getActiveIngredient();
}
