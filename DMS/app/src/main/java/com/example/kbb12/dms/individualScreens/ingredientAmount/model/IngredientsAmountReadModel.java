package com.example.kbb12.dms.individualScreens.ingredientAmount.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IngredientsAmountReadModel extends BaseReadModel {
    String getUnits();
    IIngredient getActiveIngredient();
}
