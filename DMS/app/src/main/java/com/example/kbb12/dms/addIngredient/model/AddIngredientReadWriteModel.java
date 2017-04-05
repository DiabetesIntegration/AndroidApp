package com.example.kbb12.dms.addIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;
import com.example.kbb12.dms.startUp.IIngredient;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddIngredientReadWriteModel extends BaseReadWriteModel,AddIngredientReadModel {

    void itemSearch(String search);
    boolean setScannedIngredient(String code);
    void setIngredient(String ingredient);
}
