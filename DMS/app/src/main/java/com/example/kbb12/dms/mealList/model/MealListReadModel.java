package com.example.kbb12.dms.mealList.model;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface MealListReadModel {
    List<String> getSavedMeals();
    boolean isSelectedMealCustom();
}
