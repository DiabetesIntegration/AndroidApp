package com.example.kbb12.dms.mealPlannerRecord.timeCarbEatenRecord;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciaran on 3/28/2017.
 */
public interface TimeCarbEatenRecord {
    public Map<Calendar, String> getAllBasicData();
    public long addRawData(String rawData, Calendar timestamp);
    List<Double> getAllEntries(Calendar from, Calendar to);
}
