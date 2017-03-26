package com.example.kbb12.dms.model.insulinTakenRecord;

import java.util.Calendar;

/**
 * Created by kbb12 on 01/03/2017.
 */
public interface InsulinTakenRecord {
    void addEntry(Calendar time, Double dose, boolean basalInsulin);
    IInsulinTakenEntry getMostRecentBolus();
}