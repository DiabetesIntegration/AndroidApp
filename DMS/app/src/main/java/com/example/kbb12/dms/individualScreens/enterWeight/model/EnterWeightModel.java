package com.example.kbb12.dms.individualScreens.enterWeight.model;

import android.content.SharedPreferences;

import com.example.kbb12.dms.model.EnterWeightMainModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;

/**
 * Created by kbb12 on 07/04/2017.
 */

public class EnterWeightModel extends BaseModel implements EnterWeightReadWriteModel {

    private EnterWeightMainModel model;

    public EnterWeightModel(EnterWeightMainModel model){
        this.model=model;
    }

    @Override
    public void setWeight(double weight) {
        model.setWeight(weight);
    }
}
