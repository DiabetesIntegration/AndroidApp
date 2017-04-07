package com.example.kbb12.dms.model.database.bolusInsulinModel;

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
        public static final String COLUMN_TIME = "Time";
        public static final String COLUMN_IMPROVING_ICR = "ImprovingICR";
        public static final String COLUMN_ORIG_ICR = "UserEnteredICR";
        public static final String COLUMN_IMPROVING_ISF = "ImprovingISF";
        public static final String COLUMN_ORIG_ISF = "UserEnteredISF";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                    BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME + " VARCHAR(5)," +
                    BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ICR + " FLOAT," +
                    BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ICR + " FLOAT," +
                    BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVING_ISF + " FLOAT," +
                    BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_ISF + " FLOAT," +
                    "PRIMARY KEY( "+ BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME;
}