package com.example.kbb12.dms.mealAmount;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/8/2017.
 */
public interface IMealAmount extends BaseReadModel, BaseReadWriteModel {

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
