package com.example.kbb12.dms.StartUp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by lidda on 01/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GlucoseDb";


    // Table Create Statements
    // Basic table create statement for mini-test-app
    private static final String CREATE_TABLE_BASIC = "CREATE TABLE "
            + BasicdbEntry.TABLE_NAME + "(" + BasicdbEntry._ID + " INTEGER PRIMARY KEY," + BasicdbEntry.COLUMN_NAME_DATA
            + " TEXT," + BasicdbEntry.COLUMN_NAME_TIME
            + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ToDo: Create tables
        db.execSQL(CREATE_TABLE_BASIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ToDo: Delete Tables
        db.execSQL("DROP TABLE IF EXISTS " +BasicdbEntry.TABLE_NAME);

        onCreate(db);
    }

    private class BasicdbEntry implements BaseColumns {
        public static final String TABLE_NAME = "BasicTable";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_DATA = "DataString";
    }
}
