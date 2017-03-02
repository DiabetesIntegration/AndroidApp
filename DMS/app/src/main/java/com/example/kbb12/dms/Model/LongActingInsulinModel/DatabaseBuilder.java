package com.example.kbb12.dms.Model.LongActingInsulinModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/02/2017.
 */
public class DatabaseBuilder extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                    LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE + " VARCHAR(1000)," +
                    LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE + " VARCHAR(5)," +
                    LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE + " DATE," +
                    "PRIMARY KEY( "+ LongActingInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME;


    public DatabaseBuilder(Context context, int versionNumber) {
        super(context, LongActingInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, null, versionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
