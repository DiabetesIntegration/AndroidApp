package com.example.kbb12.dms.Model.InsulinTakenRecord;

/**
 * Created by kbb12 on 01/03/2017.
 */
public interface InsulinTakenRecord {
    void addEntry(int day,int month,int year,int hour,int minute,Double dose,boolean longActing);
}
