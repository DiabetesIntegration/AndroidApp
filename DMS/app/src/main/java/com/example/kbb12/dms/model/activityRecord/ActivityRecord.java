package com.example.kbb12.dms.model.activityRecord;

import java.util.Calendar;

/**
 * Created by kbb12 on 28/03/2017.
 */

public interface ActivityRecord {
    boolean insertActivityEntry(String datetime, int calories, String activity, int durhours, int durminutes);
}
