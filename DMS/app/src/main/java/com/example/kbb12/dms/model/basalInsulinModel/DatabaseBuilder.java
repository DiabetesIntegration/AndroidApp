package com.example.kbb12.dms.model.basalInsulinModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kbb12 on 07/02/2017.
 */
public class DatabaseBuilder extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ONE_TITLE + " VARCHAR(1000)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE + " VARCHAR(5)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_FOUR_TITLE + " DATE," +
                    "PRIMARY KEY( "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME;


    public DatabaseBuilder(Context context, int versionNumber) {
        super(context, BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME, null, versionNumber);
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
