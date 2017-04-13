package com.example.kbb12.dms.individualScreens.addIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddIngredientReadModel extends BaseReadModel {
    List<String> getSavedIngredients();
    IIngredient getIngredient();
}
