package com.example.kbb12.dms.Model.InsulinTakenRecord;

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
        public static final String TABLE_NAME = "InsulinTaken";
        public static final String COLUMN_ONE_TITLE = "Date";
        public static final String COLUMN_TWO_TITLE = "Time";
        public static final String COLUMN_THREE_TITLE = "LongActing";
        public static final String COLUMN_FOUR_TITLE = "Amount";
    }
}