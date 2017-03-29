package com.example.kbb12.dms.CustomIngredient;

import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;
import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.StartUp.IIngredient;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface IAddCustomIngredient extends ErrorReadModel, ErrorReadWriteModel{

    public void setCustomIngredientName(String name);
    public void setCustomIngredientNutrition(String values[]);
    public void clearActiveIngreident();

    public void getCustomIngErrorMessage(String err);
}
