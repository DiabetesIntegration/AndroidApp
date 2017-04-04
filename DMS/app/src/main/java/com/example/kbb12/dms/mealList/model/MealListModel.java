package com.example.kbb12.dms.mealList.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.MealListMainModel;
import com.example.kbb12.dms.startUp.IMeal;
import com.example.kbb12.dms.startUp.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public class MealListModel extends BaseModel implements MealListReadWriteModel {

    MealListMainModel model;

    public MealListModel(MealListMainModel model){
        this.model=model;
    }

    @Override
    public void setNewMeal() {
        model.setActiveMeal(new Meal());
    }

    @Override
    public List<String> getSavedMeals() {
        List<String> meals  = new ArrayList<String>();
        for(IMeal current:model.getSavedMeals()) {
            if(current.isCustomCarbMeal()) {
                meals.add(current.getMealName() + " - " + current.getCustomCarbsEaten() + "g of carbohydrate in meal (No ingredients)");
            }
            else {
                meals.add(current.getMealName() + " - " + current.getTotalMealCarbs() + "g of carbohydrate in meal");
            }
        }
        return meals;
    }

    @Override
    public void setMealItem(int i) {
        model.setActiveMeal(model.getSavedMeals().get(i));
    }

    @Override
    public boolean isCustomMealAtPosition() {
        return model.getActiveMeal().isCustomCarbMeal();
    }

    @Override
    public boolean removeItem(int index) {
        model.deleteMeal(model.getSavedMeals().get(index));
        notifyObserver();
        return true;
    }

}
