package com.example.kbb12.dms.Model.Insulin;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.List;

/**
 * Created by kbb12 on 23/02/2017.
 */
public interface ILongActingInsulinDatabase {
    void addEntry(LongActingInsulinEntry entry,String brandName) throws DuplicateDoseException;
    List<LongActingInsulinEntry> getEntries();
    void clearValues();
}
