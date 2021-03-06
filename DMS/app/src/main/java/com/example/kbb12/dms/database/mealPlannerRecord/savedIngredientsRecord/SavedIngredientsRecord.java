package com.example.kbb12.dms.database.mealPlannerRecord.savedIngredientsRecord;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface SavedIngredientsRecord {

    void saveIngredient(IIngredient ingredient);
    void updateIngredient(String oldName,IIngredient ingredient);
    List<IIngredient> getAllSavedIngredients();
    IIngredient getIngredientByName(String name);
    IIngredient getIngredientByBarcode(String barcode);
}
