package com.example.kbb12.dms.model.activityRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kbb12 on 28/03/2017.
 */

public class ActivityRecordDatabase implements ActivityRecord {
    
    private SQLiteDatabase write;
    
    public ActivityRecordDatabase(SQLiteDatabase write){
        this.write=write;
    }

    public boolean insertActivityEntry(String datetime, int calories, String activity, int durhours, int durminutes) {
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
}
