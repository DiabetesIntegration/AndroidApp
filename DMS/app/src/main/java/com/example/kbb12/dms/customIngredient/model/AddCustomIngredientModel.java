package com.example.kbb12.dms.customIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.AddIngredientMainModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.Ingredient;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class AddCustomIngredientModel extends BaseModel implements AddCustomIngredientReadWriteModel {


    private AddIngredientMainModel model;

    public AddCustomIngredientModel(AddIngredientMainModel model){
        this.model=model;
    }


    @Override
    public void save(String name, double carbs, double perGrams, int packetWeight) {
        //The calculations is to turn the carbs into carbs per 100g
        model.addIngredientToMeal(new Ingredient(name,(carbs/perGrams)*100,packetWeight));
    }
}
