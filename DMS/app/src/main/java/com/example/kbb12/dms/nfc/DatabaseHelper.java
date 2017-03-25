package com.example.kbb12.dms.nfc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lidda on 01/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GlucoseDb";
    private SimpleDateFormat dateFormat;


    // Table Create Statements
    // Basic table create statement for mini-test-app
    private static final String CREATE_TABLE_BASIC = "CREATE TABLE "
            + BasicdbEntry.TABLE_NAME + " (" + BasicdbEntry._ID + " INTEGER PRIMARY KEY," + BasicdbEntry.COLUMN_NAME_DATA
            + " TEXT," + BasicdbEntry.COLUMN_NAME_TIME
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
            + HistoryDbEntry.TABLE_NAME + " (" + BasicdbEntry._ID + " INTEGER PRIMARY KEY," + BasicdbEntry.COLUMN_NAME_DATA
            + " FLOAT," + BasicdbEntry.COLUMN_NAME_TIME
            + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ToDo: Create tables
        db.execSQL(CREATE_TABLE_BASIC);
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ToDo: Delete Tables
        db.execSQL("DROP TABLE IF EXISTS " +BasicdbEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +HistoryDbEntry.TABLE_NAME);

        onCreate(db);
    }



    private String getDateTime(Calendar timestamp) {
        return dateFormat.format(timestamp.getTime());
    }

    private Calendar parseCalendar(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            return null;
        }
        return c;
    }

    private class BasicdbEntry implements BaseColumns {

    }


}