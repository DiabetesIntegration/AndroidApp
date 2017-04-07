package com.example.kbb12.dms.individualScreens.mealList.model;

import com.example.kbb12.dms.reusableFunctionality.customListView.IDeleteCustomItem;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface MealListReadWriteModel extends BaseReadWriteModel,MealListReadModel,IDeleteCustomItem {
    void setNewMeal();
    void selectMeal(int i);
}
