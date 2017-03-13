package com.example.kbb12.dms.StartUp;

import java.util.List;

/**
 * Created by Ciaran on 3/10/2017.
 */
public interface IMealPlanner {
    public boolean addMeal();
    public boolean addCustomIngredient();
    public boolean removeMeal(int i);
    public boolean removeIngredient(int i);
    public IMeal getMeal(int i);
    public List<IMeal> getAllMeals();
    public List<IIngredient> getActiveIngredients();
    public void setActiveIngredients(List<IIngredient> ing);
    public void setActiveMealName(String meal);
    public String getActiveMealName();
    public String getUnits();
    public void setUnits(String unit);

    public List<IIngredient> getSavedIngredients();
    public void addToSavedIngredients(List<IIngredient> ingredients);
    public void setItemSearch(String search);
    public String getItemSearch();
    public void addSearchedIngredient(String itemName);
}
