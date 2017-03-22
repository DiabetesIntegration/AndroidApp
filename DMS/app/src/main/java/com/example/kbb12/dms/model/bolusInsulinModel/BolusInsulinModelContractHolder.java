package com.example.kbb12.dms.model.bolusInsulinModel;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class BolusInsulinModelContractHolder {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BolusInsulinModelContractHolder() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME="BolusModel";
        public static final String COLUMN_ONE_TITLE = "Time";
        public static final String COLUMN_TWO_TITLE = "ImprovingICR";
        public static final String COLUMN_THREE_TITLE = "UserEnteredICR";
        public static final String COLUMN_FOUR_TITLE = "ImprovingISF";
        public static final String COLUMN_FIVE_TITLE = "UserEnteredISF";
    }
}