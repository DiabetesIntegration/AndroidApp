package com.example.kbb12.dms.MealAmount;

import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;
import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;

/**
 * Created by Ciaran on 3/8/2017.
 */
public interface IMealAmount extends ErrorReadModel, ErrorReadWriteModel{

    public void setMealCarbAmount(String amount);
    public boolean mealExists();
    public void addNewMeal();
    public void setMealListView();

    public void saveNewIngredients();
    public void editMeal();

    public void getMealAmountErrorMessage(String err);

}
