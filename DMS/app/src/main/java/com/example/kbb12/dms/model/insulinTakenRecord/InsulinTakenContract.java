package com.example.kbb12.dms.model.insulinTakenRecord;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class InsulinTakenContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private InsulinTakenContract() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "InsulinTakenRecord";
        public static final String COLUMN_DATE_TIME = "Date";
        public static final String COLUMN_BASAL = "Basal";
        public static final String COLUMN_AMOUNT = "Amount";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContentsDefinition.TABLE_NAME + " (" +
                    ContentsDefinition.COLUMN_DATE_TIME + " DATE," +
                    ContentsDefinition.COLUMN_BASAL + " BOOLEAN," +
                    ContentsDefinition.COLUMN_AMOUNT + " FLOAT," +
                    "PRIMARY KEY( "+ ContentsDefinition.COLUMN_DATE_TIME +", "+ContentsDefinition.COLUMN_BASAL +" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InsulinTakenContract.ContentsDefinition.TABLE_NAME;
}