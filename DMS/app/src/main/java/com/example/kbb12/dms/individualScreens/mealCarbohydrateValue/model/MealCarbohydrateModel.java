package com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.MealCarbohydrateMainModel;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.model.database.mealPlannerRecord.Meal;

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
        if(model.getActiveMeal().getName().equals(name)){
            return false;
        }
        for(IMeal current:model.getSavedMeals()) {
            if(current.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewCarbMeal(String name, int value) {
        model.saveMeal(new Meal(name,value));
        model.setActiveMeal(null);
        model.setActiveIngredient(null);
    }

    @Override
    public void eatCarbs(int amount) {
        model.registerCarbs(amount);
    }

    @Override
    public boolean hasMeal() {
        return !model.getActiveMeal().getName().equals("");
    }

    @Override
    public String getMealName() {
        return model.getActiveMeal().getName();
    }

    @Override
    public Integer getMealCarbs() {
        return (int) model.getActiveMeal().getNumCarbs();
    }
}
