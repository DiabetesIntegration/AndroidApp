package com.example.kbb12.dms.individualScreens.mealAmount.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/8/2017.
 */
public interface MealAmountReadWriteModel extends BaseReadModel, BaseReadWriteModel {
    void eatCurrentMeal(int percentEaten);
}
