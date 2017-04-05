package com.example.kbb12.dms.model.mealPlannerRecord;

import java.util.List;

/**
 * Created by Ciaran on 3/7/2017.
 */
public interface IMeal {
    String getName();
    List<IIngredient> getIngredients();
    List<Double> getAmounts();
    void addIngredient(IIngredient ingredient,double amount);
    void updateAmount(IIngredient ingredient,double amount);
    Double getAmountOf(IIngredient ingredient);
    void removeIngredient(int index);
    double getNumCarbs();
    boolean isCustomCarbMeal();
}
