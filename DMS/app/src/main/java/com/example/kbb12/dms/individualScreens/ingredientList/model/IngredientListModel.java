package com.example.kbb12.dms.individualScreens.ingredientList.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IngredientsListMainModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;
import com.example.kbb12.dms.database.mealPlannerRecord.Meal;

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
        model.updateActiveMeal(active);
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
        model.updateAndSaveActiveMeal(active);
    }

    @Override
    public void newActiveIngredient() {
        model.setActiveIngredient(null);
    }

    @Override
    public IMeal getActiveMeal() {
        return model.getActiveMeal();
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
