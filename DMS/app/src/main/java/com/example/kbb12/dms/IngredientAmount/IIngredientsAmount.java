package com.example.kbb12.dms.IngredientAmount;

import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;
import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.StartUp.IIngredient;

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
