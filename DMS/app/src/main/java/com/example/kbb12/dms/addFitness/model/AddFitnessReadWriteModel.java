package com.example.kbb12.dms.addFitness.model;

import com.example.kbb12.dms.baseScreen.model.ErrorReadModel;
import com.example.kbb12.dms.baseScreen.model.ErrorReadWriteModel;

/**
 * Created by Garry on 13/03/2017.
 */

public interface AddFitnessReadWriteModel extends AddFitnessReadModel, ErrorReadWriteModel {

    public void setActivityType(String activity);
    public void setActDateToChange(boolean change);
    public void setActTimeToChange(boolean change);
    public void setDateTaken(int year, int month, int day);
    public void setTimeTaken(int hour, int minute);
    public void setDurHours(int hour);
    public void setDurMins(int mins);
    public void saveActivity();
}
