package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface MealListMainModel {
    void setActiveMeal(IMeal activeMeal);
    void setActiveIngredient(IIngredient ingredient);
    IMeal getActiveMeal();
    List<IMeal> getSavedMeals();
    void deleteMeal(IMeal meal);
}
