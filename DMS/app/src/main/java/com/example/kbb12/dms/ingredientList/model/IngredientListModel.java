package com.example.kbb12.dms.ingredientList.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IngredientsListMainModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.model.mealPlannerRecord.Meal;

import java.util.List;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class IngredientListModel extends BaseModel implements IngredientListReadWriteModel {

    private IngredientsListMainModel model;
    private IMeal active;

    public IngredientListModel(IngredientsListMainModel model){
        this.model=model;
        active=model.getActiveMeal();
    }


    @Override
    public String getMealName() {
        return active.getName();
    }

    @Override
    public void setMealName(String mealName) {
        active=new Meal(mealName,active.getIngredients(),active.getAmounts());
        notifyObserver();
    }

    @Override
    public void setEditableIngredientPosition(int pos) {
        model.setActiveIngredient(active.getIngredients().get(pos));
    }

    @Override
    public boolean checkMealName() {
        return model.mealNameUsed(active.getName());
    }

    @Override
    public void saveMeal() {
        model.updateActiveMeal(active);
    }

    @Override
    public void newActiveIngredient() {
        model.setActiveIngredient(null);
    }

    @Override
    public List<IIngredient> getIngredients() {
        return active.getIngredients();
    }

    @Override
    public Double getAmountOf(IIngredient ingredient) {
        return active.getAmountOf(ingredient);
    }

    @Override
    public boolean removeItem(int index) {
        active.removeIngredient(index);
        notifyObserver();
        return false;
    }
}
