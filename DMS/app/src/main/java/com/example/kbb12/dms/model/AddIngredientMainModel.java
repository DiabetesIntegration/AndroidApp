package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface AddIngredientMainModel {
    List<IIngredient> getSavedIngredients();
    IIngredient getIngredientByName(String name);
    IIngredient getIngredientByBarcode(String barcode);
    void setActiveIngredient(IIngredient ingredient);
    IIngredient getActiveIngredient();
    IMeal getActiveMeal();
}
