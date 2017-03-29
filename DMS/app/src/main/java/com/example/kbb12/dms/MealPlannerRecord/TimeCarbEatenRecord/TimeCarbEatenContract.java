package com.example.kbb12.dms.MealPlannerRecord.TimeCarbEatenRecord;

import android.provider.BaseColumns;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class TimeCarbEatenContract {

    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "timeCarbEaten";
        public static final String COLUMN_NAME_DATETIME = "date";
        public static final String COLUMN_NAME_CARBCONSUMED = "carbsConsumed";
    }

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + ContentsDefinition.TABLE_NAME + " (" + ContentsDefinition._ID + " INTEGER PRIMARY KEY," + ContentsDefinition.COLUMN_NAME_DATETIME
                + " DATETIME," + ContentsDefinition.COLUMN_NAME_CARBCONSUMED
                + " TEXT" + ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
