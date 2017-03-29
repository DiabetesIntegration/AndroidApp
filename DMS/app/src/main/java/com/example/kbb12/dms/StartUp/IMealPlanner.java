package com.example.kbb12.dms.startUp;

import java.util.List;

/**
 * Created by Ciaran on 3/10/2017.
 */
public interface IMealPlanner {
    public void setSavedMeals(List<IMeal> meals);
    public void setSavedIngredients(List<IIngredient> ingredients);



    public void setActiveMeal(IMeal m);
    public IMeal getActiveMeal();
    public void setActiveIngredient(IIngredient i);
    public IIngredient getActiveIngredient();
    public boolean addNewMeal();
    public boolean addNewIngredient();
    public List<IIngredient> getMealIngredients();
    public void setMealIngredients(List<IIngredient> ing);
    public void removeCreatedIngredient();
    public void setNewIngredient(boolean nIng);
    public boolean isNewIngredient();
    public List<IMeal> getSavedMeals();
    public List<IIngredient> addToSavedIngredients(List<IIngredient> ingredients);
    public List<IIngredient> getSavedIngredients();

    public void setItemSearch(String search);
    public String getItemSearch();

    public void addSearchedIngredient(String itemName);
}
