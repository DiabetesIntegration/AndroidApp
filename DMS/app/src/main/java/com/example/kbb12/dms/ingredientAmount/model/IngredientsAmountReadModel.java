package com.example.kbb12.dms.ingredientAmount.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IngredientsAmountReadModel extends BaseReadModel {
    String getUnits();
    IIngredient getActiveIngredient();
}
