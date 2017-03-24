package com.example.kbb12.dms.takeInsulin.model;

import com.example.kbb12.dms.errorHandling.ErrorReadModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinReadModel extends ErrorReadModel {

    public enum InsulinType{
        NOT_SET, BASAL,BOLUS
    }

    Double getRecommendedUnits();

    InsulinType getRecommendedType();

    Double getAmountTaken();

    int getDayTaken();

    int getMonthTaken();

    int getYearTaken();

    int getHourTaken();

    int getMinuteTaken();

    boolean getTimeToChange();

    boolean getDateToChange();

    InsulinType getTypeTaken();

    String getCalculationDescription();
}
