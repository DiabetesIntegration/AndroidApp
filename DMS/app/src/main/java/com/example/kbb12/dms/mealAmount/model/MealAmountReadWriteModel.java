package com.example.kbb12.dms.mealAmount.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/8/2017.
 */
public interface MealAmountReadWriteModel extends BaseReadModel, BaseReadWriteModel {
    void eatCurrentMeal(int percentEaten);
}
