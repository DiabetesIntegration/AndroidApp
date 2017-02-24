package com.example.kbb12.dms.TakeInsulin.Model;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;
import com.example.kbb12.dms.Model.TakeInsulinMainModel;
import com.example.kbb12.dms.StartUp.ModelObserver;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinModel implements TakeInsulinReadWriteModel {

    TakeInsulinMainModel model;
    Double recommended;
    Double actual;
    InsulinType type;
    Integer day;
    Integer month;
    Integer year;
    Integer hour;
    Integer minute;
    String errorMessage;
    ModelObserver observer;

    public TakeInsulinModel(TakeInsulinMainModel model){
        this.model=model;
        Calendar now = Calendar.getInstance();
        day=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        year=now.get(Calendar.YEAR);
        hour=now.get(Calendar.HOUR_OF_DAY);
        minute=now.get(Calendar.MINUTE);
        //Gets most recent untaken expected dose before now and sets
        //all the entries before that dose to taken because it must
        //now be too late to take them.
        LongActingInsulinEntry entry = model.getLatestLongActingRecommendation(now);
        if(null==entry){
            recommended=0.0;
            actual=0.0;
            type=InsulinType.NOT_SET;
        }else{
            recommended=entry.getDose();
            actual=entry.getDose();
            type=InsulinType.LONG_ACTING;
        }
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
    }

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
    }

    private void notifyObserver() {
        observer.update();
    }

    @Override
    public Double getRecommendedUnits() {
        return recommended;
    }

    @Override
    public Double getAmountTaken() {
        return actual;
    }

    @Override
    public int getDayTaken() {
        return day;
    }

    @Override
    public int getMonthTaken() {
        return month;
    }

    @Override
    public int getYearTaken() {
        return year;
    }

    @Override
    public int getHourTaken() {
        return hour;
    }

    @Override
    public int getMinuteTaken() {
        return minute;
    }

    @Override
    public InsulinType getTypeTaken() {
        return type;
    }

    @Override
    public String getError() {
        return errorMessage;
    }
}
