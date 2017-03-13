package com.example.kbb12.dms.IngredientAmount;

import com.example.kbb12.dms.StartUp.IIngredient;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IIngredientsAmount {

    public void setIngredientListView();

    public void setIngredientAmount(String amount);
    public String getIngredientAmount();

    public void setUnits(String unit);
    public String getUnits();

    public void calculateCarbValOfIngredient(String amount);
}
