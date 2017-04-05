package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface MealListMainModel {
    void setActiveMeal(IMeal activeMeal);
    IMeal getActiveMeal();
    List<IMeal> getSavedMeals();
    void deleteMeal(IMeal meal);
}
