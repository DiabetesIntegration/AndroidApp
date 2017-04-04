package com.example.kbb12.dms.ingredientList;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;

import java.util.List;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IIngredientList extends BaseReadModel, BaseReadWriteModel {

    public List<String> getIngredientsInMeal();
    public String getMealName();
    public boolean fromIngredient();
    public void removeIngredientFromMeal();
    public void setMealName(String n);
    public void setIngListView(boolean meal);
    public void setIngredientsForMeal();
    public void setIngredientItem(int i);
    public void setTotalCarbs();

    public boolean checkMealName();

    public void getIngListErrorMessage(String err);
}
