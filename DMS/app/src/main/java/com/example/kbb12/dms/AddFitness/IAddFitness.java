package com.example.kbb12.dms.AddFitness;

import android.content.Context;

/**
 * Created by Garry on 13/03/2017.
 */

public interface IAddFitness {

    public void setActivityType(String activity);
    public String getActivityType();

    public void setActDateToChange();
    public boolean getActDateToChange();

    public void setActTimeToChange();
    public boolean getActTimeToChange();

    public void setDateTaken(int year, int month, int day);
    /*public String getDateTaken();

    public void setDayTaken(int day);
    public void setMonthTaken(int month);
    public void setYearTaken(int year);*/

    public int getDayTaken();
    public int getMonthTaken();
    public int getYearTaken();

    public void setTimeTaken(int hour, int minute);
    /*public String getTimeTaken();

    public void setHourTaken(int hour);
    public void setMinuteTaken(int minute);*/

    public int getHourTaken();
    public int getMinuteTaken();

    public void setDurHours(int hour);
    public int getDurhours();

    public void setDurMins(int mins);
    public int getDurMins();

    public void saveActivity(Context context, String activity, int year, int month, int day, int hours, int mins, int durhour, int durmin, double weight);
}
