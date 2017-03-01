package com.example.kbb12.dms.TakeInsulin.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;
import com.example.kbb12.dms.TakeInsulin.Controller.InsulinTypeChoiceListener;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinReadModel extends ErrorReadModel {

    public enum InsulinType{
        NOT_SET,LONG_ACTING,SHORT_ACTING
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
}
