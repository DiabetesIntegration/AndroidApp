package com.example.kbb12.dms.model;

import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lidda on 25/03/2017.
 */

public interface IBloodGlucoseModel {
    void addRawData(Calendar c, String data);
    void addHistoryReading(Calendar c, double reading);
    void addCurrentReading(Calendar c, double reading);
    BGReading getMostRecentHistoryReading();
    List<BGReading> getHistoryBetween(Calendar from, Calendar to);

}
