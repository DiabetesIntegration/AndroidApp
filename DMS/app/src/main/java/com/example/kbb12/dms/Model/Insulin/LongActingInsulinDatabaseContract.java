package com.example.kbb12.dms.Model.Insulin;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class LongActingInsulinDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LongActingInsulinDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "LongActingInsulin";
        public static final String COLUMN_ONE_TITLE = "Name";
        public static final String COLUMN_TWO_TITLE = "Time";
        public static final String COLUMN_THREE_TITLE = "Amount";
    }
}