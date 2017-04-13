package com.example.kbb12.dms.database.activityRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kbb12 on 28/03/2017.
 */

public class ActivityRecordDatabase implements ActivityRecord {
    
    private SQLiteDatabase write;
    
    public ActivityRecordDatabase(SQLiteDatabase write){
        this.write=write;
    }

    public boolean insertActivityEntry(Calendar calendar, int calories, String activity, int durhours, int durminutes) {
        String datetime=formatDateTime(calendar);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ActivityRecordContract.ContentsDefinition.DATETIME, datetime);
        contentValues.put(ActivityRecordContract.ContentsDefinition.CALORIES, calories);
        contentValues.put(ActivityRecordContract.ContentsDefinition.ACTIVITY_TYPE, activity);
        contentValues.put(ActivityRecordContract.ContentsDefinition.DURATION_HRS, durhours);
        contentValues.put(ActivityRecordContract.ContentsDefinition.DURATION_MIN, durminutes);
        System.out.println("inserting activity entry: " + calories + " cal to " + datetime);
        write.insert(ActivityRecordContract.ContentsDefinition.TABLE_NAME, null, contentValues);
        return true;
    }

    private String formatDateTime(Calendar calendar){
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = calendar.getTime();
        String datetime = format.format(date);
        return datetime;
    }

    @Override
    public List<IFitnessEntry> getAllEntries(Calendar from, Calendar to) {
        String selectQuery = "SELECT * FROM " + ActivityRecordContract.ContentsDefinition.TABLE_NAME + " WHERE " +
                ActivityRecordContract.ContentsDefinition.DATETIME + ">='" + formatDateTime(from) +
                "' AND " + ActivityRecordContract.ContentsDefinition.DATETIME + "<='" + formatDateTime(to) +
                "' ORDER BY " + ActivityRecordContract.ContentsDefinition.DATETIME + " DESC";
        List<IFitnessEntry> readings = new ArrayList<>();
        Cursor c = write.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                readings.add(new FitnessEntry());
            } while (c.moveToNext());
        }
        c.close();
        return readings;
    }
}
