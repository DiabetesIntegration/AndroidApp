package com.example.kbb12.dms.model.dailyFitnessInfo;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class DailyFitnessInfoContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DailyFitnessInfoContract() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "daytable";
        public static final String COLUMN_ID = "id";
        public static final String DATE = "date";
        public static final String CALORIES = "calories";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContentsDefinition.TABLE_NAME + "(" + ContentsDefinition.COLUMN_ID +
                    " INTEGER PRIMARY KEY, " + ContentsDefinition.DATE + " TEXT, " +
                    ContentsDefinition.CALORIES + " INTEGER)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}