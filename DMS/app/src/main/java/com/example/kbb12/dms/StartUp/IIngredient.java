package com.example.kbb12.dms.StartUp;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IIngredient {
    public void setIngredientName(String name);
    public String getIngredientName();
    public void addCustomNutrition(String vals[]);
    public String[] getNutritionalValues();
    public void setCarbAmount(String val);
    public void setCarbAmountByWeight(String weight);
    public void setCarbAmountByPercent(String percent);
    public String getCarbAmount();
    public String getUnit();
    public void setUnit(String unit);
    public void setExists(boolean exist);
    public boolean ingredientExists();
    }
