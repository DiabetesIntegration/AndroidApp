package com.example.kbb12.dms.takeInsulin.model;

import com.example.kbb12.dms.baseScreen.model.ErrorReadWriteModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinReadWriteModel extends TakeInsulinReadModel,ErrorReadWriteModel {

    void setAmountTaken(Double amountTaken);

    void setTimeToChange(boolean timeToChange);

    void setDateToChange(boolean dateToChange);

    void setTypeTaken(InsulinType typeTaken);

    void setDateTaken(int day,int month,int year);

    void setTimeTaken(int hour,int minute);

    void takeInsulin();
}
