package com.example.kbb12.dms.individualScreens.fitnessInfo.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Garry on 07/03/2017.
 */

public interface IFitnessInfo extends BaseReadWriteModel {
    int getCalCount();
    double getWeight();
}
