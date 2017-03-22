package com.example.kbb12.dms.model.basalInsulinModel;

import com.example.kbb12.dms.basalInsulinModelBuilder.view.BasalInsulinEntry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 23/02/2017.
 */
public interface IBasalInsulinModel {
    void addEntry(BasalInsulinEntry entry,String brandName,int day,int month,int year) throws DuplicateDoseException;
    List<BasalInsulinEntry> getEntries();
    void clearValues();

    BasalInsulinEntry getLatestBefore(int hour, int minute);

    Calendar getLastTakenAprox(BasalInsulinEntry mostRecent);

    void allTakenBefore(Integer hour, Integer minute, int day, int month, int year);

    void log();
}
