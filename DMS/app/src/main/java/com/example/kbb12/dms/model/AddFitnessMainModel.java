package com.example.kbb12.dms.model;

import java.util.Calendar;

/**
 * Created by kbb12 on 28/03/2017.
 */

public interface AddFitnessMainModel {
    void addToCalCount(Calendar calendar,int cal);
    void saveActivity(Calendar calendar,String activitytype,int durhour,int durmin);
}
