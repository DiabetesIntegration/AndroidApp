package com.example.kbb12.dms.model.insulinTakenRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 01/03/2017.
 */
public interface InsulinTakenRecord {
    void addEntry(int day,int month,int year,int hour,int minute,Double dose,boolean basalInsulin);
    IInsulinTakenEntry getMostRecentBolus();
    List<IInsulinTakenEntry> getAllEntries(Calendar from,Calendar to);
}