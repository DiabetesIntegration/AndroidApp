package com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/14/2017.
 */
public interface MealCarbohydrateReadWriteModel extends MealCarbohydrateReadModel,BaseReadWriteModel {

    boolean isNameTaken(String name);

    void addNewCarbMeal(String name,int value);

    void eatCarbs(int amount);

}
