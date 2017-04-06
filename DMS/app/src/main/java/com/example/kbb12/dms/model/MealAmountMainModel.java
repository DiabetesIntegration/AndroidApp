package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

/**
 * Created by kbb12 on 06/04/2017.
 */

public interface MealAmountMainModel {
    void eatCurrentMeal(double percentEaten);
    void setActiveMeal(IMeal meal);
}
