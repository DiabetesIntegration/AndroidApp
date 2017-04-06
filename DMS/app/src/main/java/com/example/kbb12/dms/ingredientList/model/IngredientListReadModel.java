package com.example.kbb12.dms.ingredientList.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IngredientListReadModel extends BaseReadModel {

    String getMealName();
    List<IIngredient> getIngredients();
    Double getAmountOf(IIngredient ingredient);
}
