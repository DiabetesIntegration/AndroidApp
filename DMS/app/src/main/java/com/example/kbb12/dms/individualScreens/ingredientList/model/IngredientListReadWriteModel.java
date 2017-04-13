package com.example.kbb12.dms.individualScreens.ingredientList.model;

import com.example.kbb12.dms.reusableFunctionality.customListView.ICustomListViewModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.database.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IngredientListReadWriteModel extends BaseReadWriteModel,ICustomListViewModel,IngredientListReadModel {

    void setMealName(String mealName);
    void setEditableIngredientPosition(int pos);
    boolean checkMealName();
    void saveMeal();
    void newActiveIngredient();
    IMeal getActiveMeal();
}
