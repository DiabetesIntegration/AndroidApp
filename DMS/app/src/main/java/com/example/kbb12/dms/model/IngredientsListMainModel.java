package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

/**
 * Created by kbb12 on 05/04/2017.
 */

public interface IngredientsListMainModel {
    IMeal getActiveMeal();
    boolean mealNameUsed(String name);
    void setActiveIngredient(IIngredient ingredient);
    void updateAndSaveActiveMeal(IMeal meal);
    void updateActiveMeal(IMeal meal);
}
