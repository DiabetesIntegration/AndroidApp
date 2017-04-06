package com.example.kbb12.dms.ingredientList.model;

import com.example.kbb12.dms.baseScreen.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IngredientListReadWriteModel extends BaseReadWriteModel,IDeleteCustomItem,IngredientListReadModel {

    void setMealName(String mealName);
    void setEditableIngredientPosition(int pos);
    boolean checkMealName();
    void saveMeal();
    void newActiveIngredient();
    IMeal getActiveMeal();
}
