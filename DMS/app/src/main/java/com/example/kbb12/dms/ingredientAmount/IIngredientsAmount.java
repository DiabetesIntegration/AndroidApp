package com.example.kbb12.dms.ingredientAmount;

import com.example.kbb12.dms.errorHandling.ErrorReadModel;
import com.example.kbb12.dms.errorHandling.ErrorReadWriteModel;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IIngredientsAmount extends ErrorReadModel, ErrorReadWriteModel {

    public void setUnits(String unit);
    public String getUnits();
    public void setCarbValOfIngredient(String amount);
    public void setIngredientListView();

    public void setIngredientExists(boolean exist);
    public boolean ingredientExists();
    public void addNewIngredient();

    public void getIngAmountErrorMessage(String err);
}
