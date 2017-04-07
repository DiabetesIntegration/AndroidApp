package com.example.kbb12.dms.model.database.insulinTakenRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 01/03/2017.
 */
public interface InsulinTakenRecord {
    void addEntry(Calendar time, Double dose, boolean basalInsulin);
    IInsulinTakenEntry getMostRecentBolus();
    List<IInsulinTakenEntry> getAllEntries(Calendar from,Calendar to);
}