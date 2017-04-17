package com.example.kbb12.dms.database.mealPlannerRecord;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IMeal {
    String getName();
    List<IIngredient> getIngredients();
    List<Double> getAmounts();
    void addIngredient(IIngredient ingredient,double amount);
    void setAmountOf(IIngredient ingredient, double amount);
    Double getAmountOf(IIngredient ingredient);
    void removeIngredient(int index);
    void removeIngredient(IIngredient ingredient);
    double getNumCarbs();
    boolean isCustomCarbMeal();
}
