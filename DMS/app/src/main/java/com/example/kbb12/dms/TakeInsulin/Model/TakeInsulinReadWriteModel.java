package com.example.kbb12.dms.TakeInsulin.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public interface TakeInsulinReadWriteModel extends TakeInsulinReadModel,ErrorReadWriteModel {

    void setAmountTaken(Double amountTaken);

    void setTimeToChange();

    void setDateToChange();

    void setTypeTaken(InsulinType typeTaken);

    void setDateTaken(int day,int month,int year);

    void setTimeTaken(int hour,int minute);

    void takeInsulin();
}
