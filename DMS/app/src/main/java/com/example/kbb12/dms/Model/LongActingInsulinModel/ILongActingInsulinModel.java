package com.example.kbb12.dms.Model.LongActingInsulinModel;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 23/02/2017.
 */
public interface ILongActingInsulinModel {
    void addEntry(LongActingInsulinEntry entry,String brandName,int day,int month,int year) throws DuplicateDoseException;
    List<LongActingInsulinEntry> getEntries();
    void clearValues();

    LongActingInsulinEntry getLatestBefore(int hour, int minute);

    Calendar getLastTakenAprox(LongActingInsulinEntry mostRecent);

    void allTakenBefore(Integer hour, Integer minute, int day, int month, int year);
}
