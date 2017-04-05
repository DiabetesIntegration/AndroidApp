package com.example.kbb12.dms.ingredientList.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IngredientsListMainModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class IngredientListModel extends BaseModel implements IngredientListReadWriteModel {

    private IngredientsListMainModel model;

    public IngredientListModel(IngredientsListMainModel model){
        this.model=model;
    }

    @Override
    public boolean removeItem(int index) {
        return false;
    }

    @Override
    public String getMealName() {
        return null;
    }

    @Override
    public void setMealName(String mealName) {

    }

    @Override
    public void setEditableIngredientPosition(int pos) {

    }

    @Override
    public boolean checkMealName() {
        return false;
    }

    @Override
    public List<IIngredient> getIngredients() {
        return null;
    }
}
