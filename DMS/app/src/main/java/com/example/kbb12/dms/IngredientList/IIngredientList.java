package com.example.kbb12.dms.IngredientList;

import java.util.List;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IIngredientList {
    public void setIngListView(boolean meal);
    public List<String> getIngredientsInMeal();
    public void setIngredientItem(int i);

    public void setMealName(String name);
    public String getMealName();
    public void createMeal();
    public void addNewIngredients();
}
