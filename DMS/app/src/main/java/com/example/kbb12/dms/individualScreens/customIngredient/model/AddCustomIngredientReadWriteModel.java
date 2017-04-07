package com.example.kbb12.dms.individualScreens.customIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddCustomIngredientReadWriteModel extends AddCustomIngredientReadModel, BaseReadWriteModel {

    void save(String name,double carbs,double perGrams,int packetWeight);
}
