package com.example.kbb12.dms.model.bloodGlucoseRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lidda on 25/03/2017.
 */

public interface BGRecord {
    public List<BGReading> getReadingsBetween(Calendar from, Calendar to);
    public BGReading getMostRecentReading();
    public void insertReading(Calendar time, double reading);
}
