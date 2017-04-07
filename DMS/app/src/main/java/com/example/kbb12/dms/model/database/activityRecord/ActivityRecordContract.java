package com.example.kbb12.dms.model.database.activityRecord;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class ActivityRecordContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ActivityRecordContract() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "activitytable";
        public static final String COLUMN_ID = "id";
        public static final String DATETIME = "datetime";
        public static final String CALORIES = "calories";
        public static final String ACTIVITY_TYPE = "activitytype";
        public static final String DURATION_HRS = "hours";
        public static final String DURATION_MIN = "minutes";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContentsDefinition.TABLE_NAME + "(" + ContentsDefinition.COLUMN_ID +
                    " INTEGER PRIMARY KEY, " + ContentsDefinition.DATETIME +
                    " TEXT, " + ContentsDefinition.CALORIES + " INTEGER, " + ContentsDefinition.ACTIVITY_TYPE + " TEXT, " + ContentsDefinition.DURATION_HRS +
                    " INTEGER, " + ContentsDefinition.DURATION_MIN + " INTEGER)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}