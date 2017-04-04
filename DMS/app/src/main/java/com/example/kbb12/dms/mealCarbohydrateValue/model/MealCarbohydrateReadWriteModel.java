package com.example.kbb12.dms.mealCarbohydrateValue.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.startUp.IMeal;

/**
 * Created by Ciaran on 3/14/2017.
 */
public interface MealCarbohydrateReadWriteModel extends BaseReadWriteModel {

    boolean isNameTaken(String name);

    void addNewCarbMeal(String name,int value);

    void eatCarbs(int amount);

}
