package com.example.kbb12.dms.addIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.startUp.IIngredient;
import com.example.kbb12.dms.startUp.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 04/04/2017.
 */

public class AddIngredientModel extends BaseModel implements IAddIngredient {

    //-----------------------------------------------------------------------------------
    //IAddIngredient


    @Override
    public void setNewIngredient() {
        IIngredient ing = new Ingredient();
        mealPlanner.setActiveIngredient(ing);
    }

    @Override
    public void removeIngListView() {
        ingList = false;
    }

    @Override
    public void setAddIngredient(boolean ing) {
        mealPlanner.setNewIngredient(ing);
    }

    @Override
    public void itemSearch(String search) {
        mealPlanner.setItemSearch(search);
        notifyObservers();
    }


    @Override
    public List<String> getSavedIngredients() {
        List<String> itemNames = new ArrayList<String>();
        boolean match = true;
        if(mealPlanner.getItemSearch().equals("")) {
            for(int i = 0; i < mealPlanner.getSavedIngredients().size(); i++) {
                itemNames.add(mealPlanner.getSavedIngredients().get(i).getIngredientName());
            }
        }
        else {
            char enteredSearch[] = mealPlanner.getItemSearch().toCharArray();
            for(int i = 0; i < mealPlanner.getSavedIngredients().size(); i++) {
                if(enteredSearch.length > mealPlanner.getSavedIngredients().get(i).getIngredientName().length()) {
                    continue;
                }
                char ing[] = mealPlanner.getSavedIngredients().get(i).getIngredientName().substring(0, enteredSearch.length).toCharArray();
                for(int j = 0; j < enteredSearch.length; j++) {
                    if(ing[j]!=enteredSearch[j]) {
                        match = false;
                    }
                }
                if(match) {
                    itemNames.add(mealPlanner.getSavedIngredients().get(i).getIngredientName());
                }
                match = true;
            }
        }
        return itemNames;
    }

    @Override
    public void getSavedIngredient(String item) {
        mealPlanner.addSearchedIngredient(item);
    }

    @Override
    public boolean setScannedIngredient(String code) {
        List<List<String>> scanDB = scannedItemRecord.getAllSavedItems();
        if(code.equals("5000232823458")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(0).get(1);
            nut[1] = scanDB.get(0).get(2);
            nut[2] = scanDB.get(0).get(3);
            mealPlanner.getActiveIngredient().setIngredientName(scanDB.get(0).get(0));
            mealPlanner.getActiveIngredient().addCustomNutrition(nut);
            return true;
        }
        else if(code.equals("5010061001613")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(1).get(1);
            nut[1] = scanDB.get(1).get(2);
            nut[2] = scanDB.get(1).get(3);
            mealPlanner.getActiveIngredient().setIngredientName(scanDB.get(1).get(0));
            mealPlanner.getActiveIngredient().addCustomNutrition(nut);
            return true;
        }
        else if(code.equals("4002359640469")) {
            String nut[] = new String [3];
            nut[0] = scanDB.get(2).get(1);
            nut[1] = scanDB.get(2).get(2);
            nut[2] = scanDB.get(2).get(3);
            mealPlanner.getActiveIngredient().setIngredientName(scanDB.get(2).get(0));
            mealPlanner.getActiveIngredient().addCustomNutrition(nut);
            return true;
        }
        return false;
    }

}
