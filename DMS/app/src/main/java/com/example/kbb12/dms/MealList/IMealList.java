package com.example.kbb12.dms.MealList;

import com.example.kbb12.dms.StartUp.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IMealList {

    public void setIngListView();
    public void setMealItem(int i);
    public String setEmptyString();
    public List<String> getSavedMeals();
    public void getIngredientsForMeal();
    public void setNewMeal();

}
