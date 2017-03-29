package com.example.kbb12.dms.mealAmount;

import com.example.kbb12.dms.errorHandling.ErrorReadModel;
import com.example.kbb12.dms.errorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.startUp.IMeal;

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

    public void addNewMealDateCarb(String amount);
    public IMeal mealToEat();

}
