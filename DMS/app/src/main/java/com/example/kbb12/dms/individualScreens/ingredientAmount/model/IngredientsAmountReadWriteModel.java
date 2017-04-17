package com.example.kbb12.dms.individualScreens.ingredientAmount.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IngredientsAmountReadWriteModel extends BaseReadWriteModel, IngredientsAmountReadModel {

    void saveAmount(int amount);
    void setUnits(String units);
    void removeActiveIngredient();
    IMeal getActiveMeal();
}
