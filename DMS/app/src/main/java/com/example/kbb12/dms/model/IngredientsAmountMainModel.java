package com.example.kbb12.dms.model;

import com.example.kbb12.dms.model.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IMeal;

/**
 * Created by kbb12 on 05/04/2017.
 */

public interface IngredientsAmountMainModel {
    void setIngredientAmount(int amount);
    Integer getActiveIngredientPacketWeight();
    void removeActiveIngredient();
    IIngredient getActiveIngredient();
    IMeal getActiveMeal();
}
