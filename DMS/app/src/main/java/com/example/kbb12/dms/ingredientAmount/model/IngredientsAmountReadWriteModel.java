package com.example.kbb12.dms.ingredientAmount.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/6/2017.
 */
public interface IngredientsAmountReadWriteModel extends BaseReadWriteModel, IngredientsAmountReadModel {

    void saveAmount(int amount);
    void setUnits(String units);
}
