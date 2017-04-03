package com.example.kbb12.dms.mealList;

import com.example.kbb12.dms.startUp.IMeal;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IMealList {

    public void setNewMeal();
    public void setIngListView();
    public List<String> getSavedMeals();
    public void setMealItem(int i);
    public void getIngredientsForMeal();
    public boolean customMealAtPosition();

    public void addNewDateCarbMealList(String amount);
    public IMeal mealCarbToEatMealList();

    public void setScanningItems();

}
