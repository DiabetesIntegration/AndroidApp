package com.example.kbb12.dms.StartUp;

import java.util.List;

/**
 * Created by Ciaran on 3/10/2017.
 */
public interface IMealPlanner {
    public boolean addMeal();
    public void setMealName(String name, int i);
    public boolean addCustomIngredient();
    //public IIngredient getCustomIngredient();
    public boolean addIngredient(String name, List<String> nutrients);
    public boolean removeMeal(int i);
    //public boolean removeIngredient(IMeal meal, int i);
    public boolean removeIngredient(int i);
    public IMeal getMeal(int i);
    public List<IMeal> getAllMeals();
    public List<IIngredient> getActiveIngredients();
    public void setActiveIngredients(List<IIngredient> ing);
    public List<IIngredient> getAllIngredients(IMeal meal);

    public void setActiveMealName(String meal);
    public String getActiveMealName();

    public String getUnits();
    public void setUnits(String unit);
    //public void setMeasurementsByGrams(boolean amount);
    //public boolean measurementByGrams();
    public String getCarbsConsumed(int i);
    //public boolean editMeal(String name, String amount, int i);
    //public boolean editIngredient(IMeal meal, String ingName, List<String> nutrients, int i);
}
