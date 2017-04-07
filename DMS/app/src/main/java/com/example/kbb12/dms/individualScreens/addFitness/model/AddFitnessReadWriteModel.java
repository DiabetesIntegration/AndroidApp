package com.example.kbb12.dms.individualScreens.addFitness.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

/**
 * Created by Garry on 13/03/2017.
 */

public interface AddFitnessReadWriteModel extends AddFitnessReadModel, BaseReadWriteModel {

    public void setActivityType(String activity);
    public void setActDateToChange(boolean change);
    public void setActTimeToChange(boolean change);
    public void setDateTaken(int year, int month, int day);
    public void setTimeTaken(int hour, int minute);
    public void setDurHours(int hour);
    public void setDurMins(int mins);
    public void saveActivity();
}
