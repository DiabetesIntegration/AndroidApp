package com.example.kbb12.dms.mealList.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.model.mealPlannerRecord.IMeal;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface MealListReadWriteModel extends BaseReadWriteModel,MealListReadModel,IDeleteCustomItem {
    void setNewMeal();
    void selectMeal(int i);
}
