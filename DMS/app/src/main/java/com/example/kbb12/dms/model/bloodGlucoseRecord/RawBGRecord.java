package com.example.kbb12.dms.model.bloodGlucoseRecord;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by lidda on 25/03/2017.
 */

public interface RawBGRecord {

    public Map<Calendar, String> getAllBasicData();
    public long addRawData(String rawData, Calendar timestamp);
}
