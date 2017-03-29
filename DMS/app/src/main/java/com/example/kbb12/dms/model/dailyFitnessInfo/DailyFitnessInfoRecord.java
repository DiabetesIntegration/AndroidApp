package com.example.kbb12.dms.model.dailyFitnessInfo;

import java.util.Calendar;

/**
 * Created by kbb12 on 28/03/2017.
 */

public interface DailyFitnessInfoRecord {
    void addToCalCount(Calendar calendar,int cal);
    int getCalCount(Calendar calendar);
}
