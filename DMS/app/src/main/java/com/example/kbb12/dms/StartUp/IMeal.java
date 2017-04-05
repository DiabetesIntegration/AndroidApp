package com.example.kbb12.dms.startUp;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IMeal {
    public void setMealName(String name);
    public String getMealName();
    public String getTotalCarbs();

    public void setTotalMealCarbs(String val);
    public String getTotalMealCarbs();

    public void setCarbsEaten();
    public String getCarbsEaten();
    public void setMealAmount(String amount);
    public String getMealAmount();
    public List<IIngredient> getAllIngredients();
    public boolean setIngredients(List<IIngredient> ing);
    void addIngredient(IIngredient ingredient);

    public void setCustomCarbMeal(boolean carb);
    public boolean isCustomCarbMeal();
    public void setCustomCarbsEaten(String amount);
    public String getCustomCarbsEaten();
}
