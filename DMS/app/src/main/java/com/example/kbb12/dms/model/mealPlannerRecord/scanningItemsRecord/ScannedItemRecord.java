package com.example.kbb12.dms.model.mealPlannerRecord.scanningItemsRecord;

import java.util.List;

/**
 * Created by Ciaran on 3/30/2017.
 */

public interface ScannedItemRecord {
    public long saveItem(String n, String cVal, String pVal, String pWeight);
    public boolean checkEntries();
    public List<List<String>> getAllSavedItems();
}
