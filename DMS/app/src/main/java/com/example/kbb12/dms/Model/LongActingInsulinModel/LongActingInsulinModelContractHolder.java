package com.example.kbb12.dms.Model.LongActingInsulinModel;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class LongActingInsulinModelContractHolder {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LongActingInsulinModelContractHolder() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static String TABLE_NAME;
        public static final String COLUMN_ONE_TITLE = "Name";
        public static final String COLUMN_TWO_TITLE = "Time";
        public static final String COLUMN_THREE_TITLE = "Amount";
        public static final String COLUMN_FOUR_TITLE = "LastTaken";
    }
}