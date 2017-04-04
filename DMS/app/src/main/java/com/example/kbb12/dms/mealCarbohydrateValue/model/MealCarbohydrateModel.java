package com.example.kbb12.dms.mealCarbohydrateValue.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.MealCarbohydrateMainModel;
import com.example.kbb12.dms.startUp.IMeal;

/**
 * Created by kbb12 on 04/04/2017.
 */

public class MealCarbohydrateModel extends BaseModel implements MealCarbohydrateReadWriteModel {

    private MealCarbohydrateMainModel model;

    public MealCarbohydrateModel(MealCarbohydrateMainModel model){
        this.model=model;
    }

    @Override
    public boolean isNameTaken(String name) {
        for(IMeal current:model.getSavedMeals()) {
            if(current.getMealName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewCarbMeal(String name, int value) {
        model.saveMeal(name,value);
    }

    @Override
    public void eatCarbs(int amount) {
        model.registerCarbs(amount);
    }
}