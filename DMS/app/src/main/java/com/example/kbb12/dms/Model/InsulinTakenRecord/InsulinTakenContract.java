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
        public static final String COLUMN_ONE_TITLE = "Date";
        public static final String COLUMN_TWO_TITLE = "Basal";
        public static final String COLUMN_THREE_TITLE = "Amount";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InsulinTakenContract.ContentsDefinition.TABLE_NAME + " (" +
                    InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE + " DATE," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE + " BOOLEAN," +
                    InsulinTakenContract.ContentsDefinition.COLUMN_THREE_TITLE + " FLOAT," +
                    "PRIMARY KEY( "+ InsulinTakenContract.ContentsDefinition.COLUMN_ONE_TITLE+", "+InsulinTakenContract.ContentsDefinition.COLUMN_TWO_TITLE+" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InsulinTakenContract.ContentsDefinition.TABLE_NAME;
}