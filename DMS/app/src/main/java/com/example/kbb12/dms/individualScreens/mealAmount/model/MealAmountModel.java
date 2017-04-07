package com.example.kbb12.dms.individualScreens.mealAmount.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.MealAmountMainModel;

/**
 * Created by kbb12 on 06/04/2017.
 */

public class MealAmountModel extends BaseModel implements MealAmountReadWriteModel {

    private MealAmountMainModel model;

    public MealAmountModel (MealAmountMainModel model){
        this.model=model;
    }


    @Override
    public void eatCurrentMeal(int percentEaten) {
        model.eatCurrentMeal(percentEaten);
        model.setActiveMeal(null);
    }
}
