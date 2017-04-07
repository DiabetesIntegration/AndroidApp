package com.example.kbb12.dms.individualScreens.fitnessInfo.model;

import android.content.SharedPreferences;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.FitnessInfoMainModel;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class FitnessInfoModel extends BaseModel implements IFitnessInfo {

    private FitnessInfoMainModel model;
    private SharedPreferences sprefs;

    public FitnessInfoModel(FitnessInfoMainModel model,SharedPreferences sprefs){
        this.model=model;
        this.sprefs=sprefs;
    }

    @Override
    public int getCalCount() {
        return model.getCalCount();
    }

    @Override
    public double getWeight() {
        return  (double) sprefs.getFloat("weight", (float)0.0);
    }

}