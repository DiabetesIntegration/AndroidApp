package com.example.kbb12.dms.ingredientList.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.model.mealPlannerRecord.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/2/2017.
 */
public interface IngredientListReadWriteModel extends BaseReadModel, BaseReadWriteModel,IDeleteCustomItem {

    String getMealName();
    void setMealName(String mealName);
    void setEditableIngredientPosition(int pos);
    boolean checkMealName();
    List<IIngredient> getIngredients();
}
