package com.example.kbb12.dms.model.database.dailyFitnessInfo;

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

public class DailyFitnessInfoDatabase implements DailyFitnessInfoRecord{
    
    private SQLiteDatabase write;
    
    public DailyFitnessInfoDatabase(SQLiteDatabase write){
        this.write=write;
    }

    public void addToCalCount(Calendar calendar, int cal){
        int calories;
        int id;
        String date = formatDate(calendar);
        if(dailyEntryExists(date)){
            Cursor cur = getDailyEntry(findDailyEntry(date));
            cur.moveToFirst();
            id = cur.getInt(cur.getColumnIndex("id"));
            calories = cur.getInt(cur.getColumnIndex("calories"));
            calories += cal;
            cur.close();

            updateDailyEntry(id, date, calories);
        } else {
            insertDailyEntry(date, cal);
        }
    }

    @Override
    public int getCalCount(Calendar calendar) {
        int calories = 0;
        String date = getDate(calendar);
        if (dailyEntryExists(date)){
            Cursor cur = getDailyEntry(findDailyEntry(date));
            cur.moveToFirst();
            calories = cur.getInt(cur.getColumnIndex("calories"));
        }
        return calories;
    }

    public String getDate(Calendar calendar) {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println("getDate date value: " + date);
        return format.format(date);
    }

    private String formatDate(Calendar calendar) {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println("getDate date value: " + date);
        return format.format(date);
    }
    
    private boolean insertDailyEntry(String date, int calories){
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DailyFitnessInfoContract.ContentsDefinition.DATE, date);
        contentvalues.put(DailyFitnessInfoContract.ContentsDefinition.CALORIES, calories);
        write.insert(DailyFitnessInfoContract.ContentsDefinition.TABLE_NAME, null, contentvalues);
        return true;
    }

    private boolean dailyEntryExists(String date){
        Cursor cursor = write.rawQuery(
                "SELECT * FROM " + DailyFitnessInfoContract.ContentsDefinition.TABLE_NAME +
                        " WHERE " + DailyFitnessInfoContract.ContentsDefinition.DATE + " = '" +
                        date + "'", null);
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



    private int findDailyEntry(String date){
        Cursor cursor = write.rawQuery("SELECT * FROM " +
                DailyFitnessInfoContract.ContentsDefinition.TABLE_NAME + " WHERE " +
                DailyFitnessInfoContract.ContentsDefinition.DATE + " = '" + date + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(DailyFitnessInfoContract.ContentsDefinition.COLUMN_ID));
    }

    private Cursor getDailyEntry(int id){
        Cursor cursor = write.rawQuery("SELECT * FROM " +
                DailyFitnessInfoContract.ContentsDefinition.TABLE_NAME +
                " WHERE " + DailyFitnessInfoContract.ContentsDefinition.COLUMN_ID + " = '" +
                id + "'", null);
        return cursor;
    }

    private boolean updateDailyEntry(int id, String date, int calories){
        System.out.println("updating daily entry for " + date);
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DailyFitnessInfoContract.ContentsDefinition.DATE, date);
        contentvalues.put(DailyFitnessInfoContract.ContentsDefinition.CALORIES, calories);
        write.update(DailyFitnessInfoContract.ContentsDefinition.TABLE_NAME, contentvalues,
                DailyFitnessInfoContract.ContentsDefinition.COLUMN_ID + " = ? ",
                new String[] {Integer.toString(id)});
        return true;
    }
}
