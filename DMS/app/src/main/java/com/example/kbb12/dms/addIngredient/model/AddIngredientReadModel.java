package com.example.kbb12.dms.addIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;

import java.util.List;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddIngredientReadModel extends BaseReadModel {
    List<String> getSavedIngredients();
}