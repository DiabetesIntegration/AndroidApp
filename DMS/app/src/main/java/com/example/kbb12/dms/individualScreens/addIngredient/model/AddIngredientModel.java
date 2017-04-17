package com.example.kbb12.dms.individualScreens.addIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.AddIngredientMainModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public class AddIngredientModel extends BaseModel implements AddIngredientReadWriteModel {

    private AddIngredientMainModel model;
    private String searchTerm;

    public AddIngredientModel(AddIngredientMainModel model){
        this.model=model;
        this.searchTerm="";
    }

    @Override
    public void itemSearch(String search) {
        this.searchTerm=search;
    }

    @Override
    public List<String> getSavedIngredients() {
        List<String> matchingIngredientNames = new ArrayList<>();
        List<IIngredient> allIngredients = model.getSavedIngredients();
        for(IIngredient ingredient:allIngredients){
            if(ingredient.getName().contains(searchTerm)){
                matchingIngredientNames.add(ingredient.getName());
            }
        }
        return matchingIngredientNames;
    }

    @Override
    public IIngredient getIngredient() {
        return model.getActiveIngredient();
    }

    @Override
    public boolean setScannedIngredient(String code) {
        IIngredient ingredient = model.getIngredientByBarcode(code);
        if(ingredient==null){
            return false;
        }
        model.setActiveIngredient(ingredient);
        return true;
    }

    @Override
    public void setIngredient(String ingredientName) {
        model.setActiveIngredient(model.getIngredientByName(ingredientName));
    }

    @Override
    public void setUpNewIngredient() {
        model.setActiveIngredient(null);
    }

    @Override
    public IMeal getActiveMeal() {
        return model.getActiveMeal();
    }

}