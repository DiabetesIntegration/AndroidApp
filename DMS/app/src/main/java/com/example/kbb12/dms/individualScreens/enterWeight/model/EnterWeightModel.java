package com.example.kbb12.dms.individualScreens.enterWeight.model;

import android.content.SharedPreferences;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;

/**
 * Created by kbb12 on 07/04/2017.
 */

public class EnterWeightModel extends BaseModel implements EnterWeightReadWriteModel {

    private SharedPreferences.Editor spredit;

    public EnterWeightModel(SharedPreferences.Editor spredit){
        this.spredit=spredit;
    }

    @Override
    public void setWeight(double weight) {
        spredit.putFloat("weight", (float) weight);
    }
}
