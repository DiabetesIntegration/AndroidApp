package com.example.kbb12.dms.model.mealPlannerRecord.scanningItemsRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 3/30/2017.
 */

public class ScannedItemDatabase implements ScannedItemRecord {
    private SQLiteDatabase write;

    public ScannedItemDatabase(SQLiteDatabase write) {
        this.write = write;
    }


    @Override
    public long saveItem(String n, String cVal, String pVal, String pWeight) {
        ContentValues values = new ContentValues();
        values.put(ScannedItemContract.ContentsDefinition.COLUMN_NAME_INGNAMESCAN, n);
        values.put(ScannedItemContract.ContentsDefinition.COLUMN_NAME_CARBVALSCAN, cVal);
        values.put(ScannedItemContract.ContentsDefinition.COLUMN_NAME_PACKETVALSCAN, pVal);
        values.put(ScannedItemContract.ContentsDefinition.COLUMN_NAME_WEIGHTSCAN, pWeight);

        // insert row
        return write.insert(ScannedItemContract.ContentsDefinition.TABLE_NAME, null, values);
    }

    @Override
    public boolean checkEntries() {


        String selectQuery = "SELECT  * FROM " + ScannedItemContract.ContentsDefinition.TABLE_NAME;

        try {
            Cursor c = write.rawQuery(selectQuery, null);
            return true;
        }
        catch(SQLiteException e) {
            return false;
        }
    }

    @Override
    public List<List<String>> getAllSavedItems() {
        List<List<String>> tableResults = new ArrayList<List<String>>();

        String selectQuery = "SELECT  * FROM " + ScannedItemContract.ContentsDefinition.TABLE_NAME;

        Cursor c = write.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                List<String> values = new ArrayList<String>();
                values.add(c.getString(c.getColumnIndex(ScannedItemContract.ContentsDefinition.COLUMN_NAME_INGNAMESCAN)));
                values.add(c.getString(c.getColumnIndex(ScannedItemContract.ContentsDefinition.COLUMN_NAME_CARBVALSCAN)));
                values.add(c.getString(c.getColumnIndex(ScannedItemContract.ContentsDefinition.COLUMN_NAME_PACKETVALSCAN)));
                values.add(c.getString(c.getColumnIndex(ScannedItemContract.ContentsDefinition.COLUMN_NAME_WEIGHTSCAN)));
                tableResults.add(values);
            } while (c.moveToNext());
        }


        return tableResults;
    }

}
