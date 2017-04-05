package com.example.kbb12.dms.customIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.AddIngredientMainModel;
import com.example.kbb12.dms.startUp.IIngredient;
import com.example.kbb12.dms.startUp.Ingredient;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class AddCustomIngredientModel extends BaseModel implements AddCustomIngredientReadWriteModel {


    private AddIngredientMainModel model;

    public AddCustomIngredientModel(AddIngredientMainModel model){
        this.model=model;
    }

    @Override
    public void save(String name, String carbs,String packet,String packetWeight) {
        String nutrients[] = {carbs, packet, packetWeight};
        IIngredient ingredient = new Ingredient(name,nutrients);
        model.addIngredientToMeal(ingredient);
    }
}
