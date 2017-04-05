package com.example.kbb12.dms.model.mealPlannerRecord.savedIngredientsRecord;

import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface SavedIngredientsRecord {

    void saveIngredient(IIngredient ingredient);
    List<IIngredient> getAllSavedIngredients();
    IIngredient getIngredientByName(String name);
    IIngredient getIngredientByBarcode(String barcode);
}
