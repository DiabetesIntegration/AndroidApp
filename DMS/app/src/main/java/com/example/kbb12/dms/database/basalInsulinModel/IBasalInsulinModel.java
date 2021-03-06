package com.example.kbb12.dms.database.basalInsulinModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 23/02/2017.
 */
public interface IBasalInsulinModel {
    void addEntry(BasalInsulinEntry entry,String brandName,int day,int month,int year) throws DuplicateDoseException;
    List<BasalInsulinEntry> getEntries(boolean usingImprovements);
    void clearValues();

    BasalInsulinEntry getLatestBefore(int hour, int minute,boolean usingImprovement);

    Calendar getLastTakenAprox(BasalInsulinEntry mostRecent);

    void allTakenBefore(Integer hour, Integer minute, int day, int month, int year);

    void improve(BasalInsulinEntry entry,Float improvement);

    void log();
}
