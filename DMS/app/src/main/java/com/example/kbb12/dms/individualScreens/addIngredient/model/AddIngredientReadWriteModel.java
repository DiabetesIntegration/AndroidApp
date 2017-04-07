package com.example.kbb12.dms.individualScreens.addIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddIngredientReadWriteModel extends BaseReadWriteModel,AddIngredientReadModel {

    void itemSearch(String search);
    boolean setScannedIngredient(String code);
    void setIngredient(String ingredient);
    void setUpNewIngredient();
    IMeal getActiveMeal();
}
