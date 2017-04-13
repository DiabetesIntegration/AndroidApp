package com.example.kbb12.dms.database.mealPlannerRecord.savedMealsRecord;

import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

import java.util.List;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface SavedMealsRecord {

    void saveMeal(IMeal meal);
    void editMeal(String oldName,IMeal meal);
    void deleteMeal(IMeal m);
    List<IMeal> getAllMeals(List<IIngredient> allIngredients);

}
