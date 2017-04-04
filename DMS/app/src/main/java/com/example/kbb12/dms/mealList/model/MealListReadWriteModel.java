package com.example.kbb12.dms.mealList.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.customListView.IDeleteCustomItem;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface MealListReadWriteModel extends BaseReadWriteModel,MealListReadModel,IDeleteCustomItem {
    void setNewMeal();
    void setMealItem(int i);
}
