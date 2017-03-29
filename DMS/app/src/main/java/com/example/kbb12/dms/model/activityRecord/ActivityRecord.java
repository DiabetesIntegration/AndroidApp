package com.example.kbb12.dms.model.activityRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 28/03/2017.
 */

public interface ActivityRecord {
    boolean insertActivityEntry(Calendar calendar, int calories, String activity, int durhours, int durminutes);
    List<IFitnessEntry> getAllEntries(Calendar from, Calendar to);
}
