package com.example.kbb12.dms.StartUp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Garry on 08/03/2017.
 */

public class dbHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "FitnessTracker.db";

    public static final String DAILY_TABLE = "daytable";

    public static final String COLUMN_ID = "id";
    public static final String DATE = "date";
    public static final String CALORIES = "calories";

    public dbHelper(Context context){
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DAILY_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + DATE + " TEXT, " + CALORIES + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_TABLE);
        onCreate(db);
    }

    public boolean insertEntry(String date, float calories){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DATE, date);
        contentvalues.put(CALORIES, calories);
        db.insert(DAILY_TABLE, null, contentvalues);
        return true;
    }

    public boolean EntryExists(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DAILY_TABLE + " WHERE " + DATE + " = '" + date + "'", null);
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int findEntry(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DAILY_TABLE + "WHERE " + DATE + " = '" + date + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
    }

    public Cursor getEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DAILY_TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        return cursor;
    }

    public boolean updateEntry(int id, String date, double calories){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(DATE, date);
        contentvalues.put(CALORIES, calories);
        db.update(DAILY_TABLE, contentvalues, COLUMN_ID + " = ? ", new String[] {Integer.toString(id)});
        return true;
    }

    public int deleteEntry(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DAILY_TABLE, COLUMN_ID + " = ? ", new String[] {Integer.toString(id)});
    }
}
