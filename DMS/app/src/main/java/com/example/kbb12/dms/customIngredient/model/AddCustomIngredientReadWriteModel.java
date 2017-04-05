package com.example.kbb12.dms.customIngredient.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;
import com.example.kbb12.dms.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Ciaran on 3/1/2017.
 */
public interface AddCustomIngredientReadWriteModel extends BaseReadModel, BaseReadWriteModel {

    void save(String name,String carbs,String packet,String packetWeight);
}
