package com.example.kbb12.dms.MealCarbohydrateValue;

import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;
import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;

/**
 * Created by Ciaran on 3/14/2017.
 */
public interface IMealCarbohydrateValue extends ErrorReadModel, ErrorReadWriteModel {

    public void setStraightCarbs(boolean straightCarbs);
    public boolean addCarbMeal();

    public void setCarbMealName(String name);
    public void setCarbMealValue(String value);

    public void addNewCarbMeal();

    public void notIngredientList(boolean list);

    public void getMealCarbohydrateErrorMessage(String err);

}
