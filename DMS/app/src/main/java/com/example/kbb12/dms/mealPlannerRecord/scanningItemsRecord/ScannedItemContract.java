package com.example.kbb12.dms.mealPlannerRecord.scanningItemsRecord;

import android.provider.BaseColumns;

/**
 * Created by Ciaran on 3/30/2017.
 */

public class ScannedItemContract {
    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "scanneritems";
        public static final String COLUMN_NAME_INGNAMESCAN = "nameScan";
        public static final String COLUMN_NAME_CARBVALSCAN = "carbValScan";
        public static final String COLUMN_NAME_PACKETVALSCAN = "packetValScan";
        public static final String COLUMN_NAME_WEIGHTSCAN = "packetWeightScan";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + ContentsDefinition.TABLE_NAME + " (" + ContentsDefinition._ID + " INTEGER PRIMARY KEY," + ContentsDefinition.COLUMN_NAME_INGNAMESCAN
            + " TEXT," + ContentsDefinition.COLUMN_NAME_CARBVALSCAN
            + " TEXT," + ContentsDefinition.COLUMN_NAME_PACKETVALSCAN
            + " TEXT," + ContentsDefinition.COLUMN_NAME_WEIGHTSCAN
            + " TEXT" + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
