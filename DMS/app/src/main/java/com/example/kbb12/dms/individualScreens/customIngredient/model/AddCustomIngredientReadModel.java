package com.example.kbb12.dms.individualScreens.customIngredient.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;

/**
 * Created by kbb12 on 06/04/2017.
 */

public interface AddCustomIngredientReadModel extends BaseReadModel {
    boolean hasExisting();
    String getIngredientName();
    Double getCarbPerHundred();
    Integer getPacketWeight();
}
