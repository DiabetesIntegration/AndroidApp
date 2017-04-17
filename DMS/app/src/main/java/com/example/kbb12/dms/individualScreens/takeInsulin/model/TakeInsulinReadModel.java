package com.example.kbb12.dms.individualScreens.takeInsulin.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinReadModel extends BaseReadModel {

    enum InsulinType{
        NOT_SET, BASAL,BOLUS
    }

    Double getRecommendedUnits();

    InsulinType getRecommendedType();

    Double getAmountTaken();

    Calendar getTimeTaken();

    boolean isTimeToChange();

    boolean isDateToChange();

    InsulinType getTypeTaken();

    String getCalculationDescription();
}
