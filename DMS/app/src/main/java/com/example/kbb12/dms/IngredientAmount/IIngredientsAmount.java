package com.example.kbb12.dms.IngredientAmount;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IIngredientsAmount {

    public boolean isWeight();
    public void changeUnit(boolean wop);

    public void setIngredientAmount(String amount);
    public String getIngredientAmount();

    public void setUnits(String unit);
    public String getUnits();
}
