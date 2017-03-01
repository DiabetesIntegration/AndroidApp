package com.example.kbb12.dms.Model.LongActingInsulinModel;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class LongActingInsulinModelInitialContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LongActingInsulinModelInitialContract() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "LongActingInsulin";
        public static final String COLUMN_ONE_TITLE = "Name";
        public static final String COLUMN_TWO_TITLE = "Time";
        public static final String COLUMN_THREE_TITLE = "Amount";
        public static final String COLUMN_FOUR_TITLE = "LastTaken";
    }
}