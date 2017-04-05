package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

/**
 * Created by kbb12 on 05/04/2017.
 */

public interface IngredientsListMainModel {
    IMeal getActiveMeal();
    boolean mealNameUsed(String name);
    void setActiveIngredient(IIngredient ingredient);
    void updateActiveMeal(IMeal meal);
}
