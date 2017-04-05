package com.example.kbb12.dms.addIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.AddIngredientMainModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.Ingredient;

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
    public boolean setScannedIngredient(String code) {
        IIngredient ingredient = new Ingredient();
        List<List<String>> scanDB = model.getAllScanableItems();
        if(code.equals("5000232823458")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(0).get(1);
            nut[1] = scanDB.get(0).get(2);
            nut[2] = scanDB.get(0).get(3);
            ingredient.setName(scanDB.get(0).get(0));
            ingredient.addCustomNutrition(nut);
            model.addIngredientToMeal(ingredient);
            return true;
        }
        else if(code.equals("5010061001613")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(1).get(1);
            nut[1] = scanDB.get(1).get(2);
            nut[2] = scanDB.get(1).get(3);
            ingredient.setIngredientName(scanDB.get(1).get(0));
            ingredient.addCustomNutrition(nut);
            model.addIngredientToMeal(ingredient);
            return true;
        }
        else if(code.equals("4002359640469")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(2).get(1);
            nut[1] = scanDB.get(2).get(2);
            nut[2] = scanDB.get(2).get(3);
            ingredient.setIngredientName(scanDB.get(2).get(0));
            ingredient.addCustomNutrition(nut);
            model.addIngredientToMeal(ingredient);
            return true;
        }
        return false;
    }

    @Override
    public void setIngredient(String ingredientName) {
        List<IIngredient> allIngredients = model.getSavedIngredients();
        for(IIngredient ingredient:allIngredients){
            if(ingredient.getName().equals(ingredientName)){
                model.addIngredientToMeal(ingredient);
                break;
            }
        }
    }

}
