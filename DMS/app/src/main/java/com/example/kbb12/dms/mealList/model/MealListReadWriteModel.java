package com.example.kbb12.dms.mealList.model;

import com.example.kbb12.dms.baseScreen.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface MealListReadWriteModel extends BaseReadWriteModel,MealListReadModel,IDeleteCustomItem {
    void setNewMeal();
    void selectMeal(int i);
    void clearActives();
}
