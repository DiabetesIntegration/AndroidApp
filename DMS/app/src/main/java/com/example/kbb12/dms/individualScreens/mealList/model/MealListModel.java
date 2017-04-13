package com.example.kbb12.dms.individualScreens.mealList.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.MealListMainModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Meal;

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
        List<String> meals  = new ArrayList<>();
        for(IMeal current:model.getSavedMeals()) {
            if(current.isCustomCarbMeal()) {
                meals.add(current.getName() + " - " + current.getNumCarbs() + "g of carbohydrate in meal (No ingredients)");
            }
            else {
                meals.add(current.getName() + " - " + current.getNumCarbs() + "g of carbohydrate in meal");
            }
        }
        return meals;
    }

    @Override
    public void selectMeal(int i) {
        model.setActiveMeal(model.getSavedMeals().get(i));
    }

    @Override
    public boolean isSelectedMealCustom() {
        return model.getActiveMeal().isCustomCarbMeal();
    }

    @Override
    public boolean removeItem(int index) {
        model.deleteMeal(model.getSavedMeals().get(index));
        notifyObserver();
        return true;
    }

}
