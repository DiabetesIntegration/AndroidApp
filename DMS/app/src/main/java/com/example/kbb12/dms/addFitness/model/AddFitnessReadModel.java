package com.example.kbb12.dms.addFitness.model;

import com.example.kbb12.dms.baseScreen.model.BaseReadModel;

/**
 * Created by Garry on 13/03/2017.
 */

public interface AddFitnessReadModel extends BaseReadModel {
    public boolean getActDateToChange();
    public boolean getActTimeToChange();
    public int getDayTaken();
    public int getMonthTaken();
    public int getYearTaken();
    public int getHourTaken();
    public int getMinuteTaken();
    public int getDurhours();
    public int getDurMins();
}
