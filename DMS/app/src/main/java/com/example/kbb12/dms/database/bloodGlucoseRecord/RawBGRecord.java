package com.example.kbb12.dms.database.bloodGlucoseRecord;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by lidda on 25/03/2017.
 */

public interface RawBGRecord {

    Map<Calendar, String> getAllBasicData();
    long addRawData(String rawData, Calendar timestamp);
}
