package com.example.kbb12.dms.individualScreens.customIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.CustomIngredientMainModel;
import com.example.kbb12.dms.database.mealPlannerRecord.Ingredient;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class AddCustomIngredientModel extends BaseModel implements AddCustomIngredientReadWriteModel {


    private CustomIngredientMainModel model;

    public AddCustomIngredientModel(CustomIngredientMainModel model){
        this.model=model;
    }


    @Override
    public void save(String name, double carbs, double perGrams, int packetWeight) {
        //The calculation is to turn the carbs into carbs per 100g
        model.saveIngredient(new Ingredient(name,(carbs/perGrams)*100,packetWeight));
    }

    @Override
    public boolean hasExisting() {
        return model.getActiveIngredient()!=null;
    }

    @Override
    public String getIngredientName() {
        return model.getActiveIngredient().getName();
    }

    @Override
    public Double getCarbPerHundred() {
        return model.getActiveIngredient().getCarbsPerHundredG();
    }

    @Override
    public Integer getPacketWeight() {
        return model.getActiveIngredient().getPacketWeight();
    }
}
